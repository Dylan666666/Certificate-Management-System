pragma solidity ^0.4.25;
pragma experimental ABIEncoderV2;

import "./Table.sol";

contract CertificateIQ{
    
    event registerResult(int ret);
    event createResult(int ret);

    /*用于临时接收查询得到的数据*/
    struct CertificateItem {
        uint256 size;
        uint256[] ID;
        string[] schoolFlag;
        string[] stuNumber;
        string[] userName;
        string[] cmsName;
        string[] cmsType;
        string[] cmsWinTime;
        string[] cmsDesc;
        string[] cmsUrl;
    }
    
    string constant TABLE_NAME = "t_cms";

    // 证书信息表, ID
    // |  证书id  |  学校标识   |    学号    |   姓名    | 证书名称 | 证书类别 |   颁发时间  | 证书描述 | 证书凭证URL | 
    // |---------|------------|-----------|----------|---------|---------|------------|---------|-----------|
    // |   ID    | schoolFlag | stuNumber | userName | cmsName | cmsType | cmsWinTime | cmsDesc |   cmsUrl  |
    // |---------|------------|-----------|----------|---------|---------|------------|---------|-----------|
    //
    // 创建表

    function create() 
    public 
    returns(int) 
    {
        TableFactory tf = TableFactory(0x1001);
        int count = tf.createTable(TABLE_NAME, "ID", "schoolFlag,stuNumber,userName,cmsName,cmsType,cmsWinTime,cmsDesc,cmsUrl");
        emit createResult(count);
        return count;
    }

    //添加证书基本信息
    function register(string schoolFlag, string stuNumber, string userName, 
        string cmsName, string cmsType, string cmsWinTime, string cmsDesc, string cmsUrl) 
    public 
    returns(int256) 
    {
        Table table = TableFactory(0x1001).openTable(TABLE_NAME);
        Entry entry = table.newEntry();
        entry.set("ID",int(1));
        entry.set("schoolFlag", schoolFlag);
        entry.set("stuNumber", stuNumber);
        entry.set("userName", userName);
        entry.set("cmsName", cmsName);
        entry.set("cmsType", cmsType);
        entry.set("cmsWinTime", cmsWinTime);
        entry.set("cmsDesc", cmsDesc);
        entry.set("cmsUrl", cmsUrl);
        int count = table.insert(uint2str(1), entry);
        int ret = 0;
        if (count != 1) {
            ret = 2;
        }
        emit registerResult(ret);
        return ret;
    }
    
    /*根据学号查询证书集合，备注：只返回部分重要信息*/
    function select(string stuNumber) 
    public 
    view 
    returns(uint256, string[], string[], string[], string[], string[]) 
    {
        Table table = TableFactory(0x1001).openTable(TABLE_NAME);
        Condition condition = table.newCondition();
        condition.EQ("stuNumber", stuNumber);
        Entries entries = table.select(uint2str(1), condition);
        uint256 size= uint256(entries.size());
        CertificateItem memory item = CertificateItem(size, new uint[](size),
            new string[](size), new string[](size), new string[](size), new string[](size),
            new string[](size), new string[](size), new string[](size), new string[](size));
        item.size = size;
        for (uint256 i = 0; i < size; i++) {
            Entry entry = entries.get(int(i));
            item.cmsName[i] = entry.getString("cmsName");
            item.cmsType[i] = entry.getString("cmsType");
            item.cmsWinTime[i] = entry.getString("cmsWinTime");
            item.cmsDesc[i] = entry.getString("cmsDesc");
            item.cmsUrl[i] = entry.getString("cmsUrl");
        }
        return (item.size, item.cmsName, item.cmsType, item.cmsWinTime, item.cmsDesc, item.cmsUrl);
    }

    function uint2str(uint256 i) constant returns (string) {
        if (i == 0) return "0";
        uint j = i;
        uint length;
        while (j != 0){
            length++;
            j /= 10;
        }
        bytes memory bstr = new bytes(length);
        uint k = length - 1;
        while (i != 0){
            bstr[k--] = byte(48 + i % 10);
            i /= 10;
        }
        return string(bstr);
    }
}