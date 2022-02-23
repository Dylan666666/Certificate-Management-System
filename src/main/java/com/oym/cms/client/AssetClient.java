package com.oym.cms.client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.oym.cms.contract.Asset;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
/**
 * @Author: Mr_OO
 * @Date: 2022/2/23 16:17
 */
public class AssetClient {
    static Logger logger = LoggerFactory.getLogger(AssetClient.class);

    private BcosSDK bcosSDK;
    private Client client;
    private CryptoKeyPair cryptoKeyPair;

    public void initialize() throws Exception {
        @SuppressWarnings("resource")
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bcosSDK = context.getBean(BcosSDK.class);
        client = bcosSDK.getClient(1);
        cryptoKeyPair = client.getCryptoSuite().createKeyPair();
        client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
        logger.debug("create client for group1, account address is " + cryptoKeyPair.getAddress());
    }

    public void deployAssetAndRecordAddr() {

        try {
            Asset asset = Asset.deploy(client, cryptoKeyPair);
            System.out.println(
                    " deploy Asset success, contract address is " + asset.getContractAddress());

            recordAssetAddr(asset.getContractAddress());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println(" deploy Asset contract failed, error message is  " + e.getMessage());
        }
    }

    public void recordAssetAddr(String address) throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("address", address);
        final Resource contractResource = new ClassPathResource("contract.properties");
        FileOutputStream fileOutputStream = new FileOutputStream(contractResource.getFile());
        prop.store(fileOutputStream, "contract address");
    }

    public String loadAssetAddr() throws Exception {
        // load Asset contact address from contract.properties
        Properties prop = new Properties();
        final Resource contractResource = new ClassPathResource("contract.properties");
        prop.load(contractResource.getInputStream());

        String contractAddress = prop.getProperty("address");
        if (contractAddress == null || contractAddress.trim().equals("")) {
            throw new Exception(" load Asset contract address failed, please deploy it first. ");
        }
        logger.info(" load Asset address from contract.properties, address is {}", contractAddress);
        return contractAddress;
    }

    public void queryAssetAmount(String assetAccount) {
        try {
            String contractAddress = loadAssetAddr();
            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
            Tuple2<BigInteger, BigInteger> result = asset.select(assetAccount);
            if (result.getValue1().compareTo(new BigInteger("0")) == 0) {
                System.out.printf(" asset account %s, value %s \n", assetAccount, result.getValue2());
            } else {
                System.out.printf(" %s asset account is not exist \n", assetAccount);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

            System.out.printf(" query asset account failed, error message is %s\n", e.getMessage());
        }
    }

    public void registerAssetAccount(String assetAccount, BigInteger amount) {
        try {
            String contractAddress = loadAssetAddr();

            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = asset.register(assetAccount, amount);
            List<Asset.RegisterEventEventResponse> response = asset.getRegisterEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
                    System.out.printf(
                            " register asset account success => asset: %s, value: %s \n", assetAccount, amount);
                } else {
                    System.out.printf(
                            " register asset account failed, ret code is %s \n", response.get(0).ret.toString());
                }
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

            logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
            System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
        }
    }

    public void transferAsset(String fromAssetAccount, String toAssetAccount, BigInteger amount) {
        try {
            String contractAddress = loadAssetAddr();
            Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
            TransactionReceipt receipt = asset.transfer(fromAssetAccount, toAssetAccount, amount);
            List<Asset.TransferEventEventResponse> response = asset.getTransferEventEvents(receipt);
            if (!response.isEmpty()) {
                if (response.get(0).ret.compareTo(new BigInteger("0")) == 0) {
                    System.out.printf(
                            " transfer success => from_asset: %s, to_asset: %s, amount: %s \n",
                            fromAssetAccount, toAssetAccount, amount);
                } else {
                    System.out.printf(
                            " transfer asset account failed, ret code is %s \n", response.get(0).ret.toString());
                }
            } else {
                System.out.println(" event log not found, maybe transaction not exec. ");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();

            logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
            System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
        }
    }

    public static void  Usage() {
        System.out.println(" Usage:");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient deploy");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient query account");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient register account value");
        System.out.println(
                "\t java -cp conf/:lib/*:apps/* org.fisco.bcos.asset.client.AssetClient transfer from_account to_account amount");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        AssetClient client = new AssetClient();
        client.initialize();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("请进行操作：");
            String str = scanner.nextLine();
            String[] strings = str.split(" ");
            if (args.length < 1) {
                Usage();
            }
            switch (strings[0]) {
                case "deploy":
                    client.deployAssetAndRecordAddr();
                    break;
                case "query":
                    if (strings.length < 2) {
                        Usage();
                    }
                    client.queryAssetAmount(strings[1]);
                    break;
                case "register":
                    if (strings.length < 3) {
                        Usage();
                    }
                    client.registerAssetAccount(strings[1], new BigInteger(strings[2]));
                    break;
                case "transfer":
                    if (strings.length < 4) {
                        Usage();
                    }
                    client.transferAsset(strings[1], strings[2], new BigInteger(strings[3]));
                    break;
                default:
                {
                    Usage();
                }
            }
        }
    }

}

