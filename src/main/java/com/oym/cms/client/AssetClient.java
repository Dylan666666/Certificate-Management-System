package com.oym.cms.client;

import com.oym.cms.contract.Asset;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Properties;

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
    client = bcosSDK.getClient();
    cryptoKeyPair = client.getCryptoSuite().getCryptoKeyPair();
    client.getCryptoSuite().setCryptoKeyPair(cryptoKeyPair);
    logger.debug("create client for group, account address is " + cryptoKeyPair.getAddress());
  }

  public void deployAssetAndRecordAddr() {

    try {
      Asset asset = Asset.deploy(client, cryptoKeyPair);
      System.out.println(
          " deploy Asset success, contract address is " + asset.getContractAddress());

      recordAssetAddr(asset.getContractAddress());
    } catch (Exception e) {
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
      Tuple2<Boolean, String> result = asset.select(assetAccount);
      if (result.getValue1()) {
        System.out.printf(" asset account %s, value %s \n", assetAccount, result.getValue2());
      } else {
        System.out.printf(" %s asset account is not exist \n", assetAccount);
      }
    } catch (Exception e) {
      logger.error(" queryAssetAmount exception, error message is {}", e.getMessage());

      System.out.printf(" query asset account failed, error message is %s\n", e.getMessage());
    }
  }

  public void registerAssetAccount(String assetAccount, String amount) {
    try {
      String contractAddress = loadAssetAddr();

      Asset asset = Asset.load(contractAddress, client, cryptoKeyPair);
      TransactionReceipt receipt = asset.register(assetAccount, amount);
      Tuple1<String> registerOutput = asset.getRegisterOutput(receipt);
      if (receipt.getStatus() == 0) {
        if (Objects.equals(registerOutput.getValue1(), BigInteger.valueOf(0))) {
          System.out.printf(
                  " register asset account success => asset: %s, value: %s \n", assetAccount, amount);
        } else {
          System.out.printf(
                  " register asset account failed, ret code is %s \n", registerOutput.getValue1());
        }
      } else {
        System.out.println(" receipt status is error, maybe transaction not exec, status is: " + receipt.getStatus());
      }
    } catch (Exception e) {
      logger.error(" registerAssetAccount exception, error message is {}", e.getMessage());
      System.out.printf(" register asset account failed, error message is %s\n", e.getMessage());
    }
  }

  public static void Usage() {
    System.out.println(" Usage:");
    System.out.println(
        "\t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.asset.client.AssetClient deploy");
    System.out.println(
        "\t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.asset.client.AssetClient query account");
    System.out.println(
        "\t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.asset.client.AssetClient register account value");
    System.out.println(
        "\t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.asset.client.AssetClient transfer from_account to_account amount");
    System.exit(0);
  }

  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      Usage();
    }

    AssetClient client = new AssetClient();
    client.initialize();

    switch (args[0]) {
      case "deploy":
        client.deployAssetAndRecordAddr();
        break;
      case "query":
        if (args.length < 2) {
          Usage();
        }
        client.queryAssetAmount(args[1]);
        break;
      case "register":
        if (args.length < 3) {
          Usage();
        }
        client.registerAssetAccount(args[1], new String(args[2]));
        break;
      default:
        {
          Usage();
        }
    }
    System.exit(0);
  }
}
