pragma solidity ^0.6.0;
pragma experimental ABIEncoderV2;

import "./KVTable.sol";

contract Asset {
    // event
    event RegisterEvent(
        int256 ret,
        string indexed cms_id,
        string indexed cms_str
    );
    
    KVTable tf;

    constructor() public {
        // 构造函数中创建t_cms表
        tf = KVTable(0x1009);
        // 资产管理表, key : account, field : asset_value
        // |  资产账户(主键)      |     资产金额       |
        // |-------------------- |-------------------|
        // |        cms_id      |    cms_str    |
        // |---------------------|-------------------|
        //
        // 创建表
        tf.createTable("t_cms", "cms_id", "cms_str");
    }

    /*
    描述 : 根据证书序列号查询证书
    参数 ：
            account : 资产账户

    返回值：
            参数一： 成功返回0, 账户不存在返回-1
            参数二： 第一个参数为0时有效，资产金额
    */
    function select(string memory cmsId) public view returns (cmsStr, string) {
        // 查询
        Entry memory entry;
        bool result;
        (result, entry) = tf.get("t_cms", cmsId);
        string cmsStr = entry.fields[0].value;
        return (result, string(cmsStr));
    }

    /*
    描述 : 资产注册
    参数 ：
            account : 资产账户
            amount  : 资产金额
    返回值：
            0  资产注册成功
            -1 资产账户已存在
            -2 其他错误
    */
    function register(string memory cms_id, string cms_str)
    public
    returns (int256)
    {
        int256 ret_code = 0;
        bool ret = true;
        string temp_asset_value = "";
        // 查询账号是否存在
        (ret, temp_asset_value) = select(cms_id);
        if (ret != true) {
            string memory asset_value_str = cms_str;
            KVField memory kv1 = KVField("cms_str", asset_value_str);
            KVField[] memory KVFields = new KVField[](1);
            KVFields[0] = kv1;
            Entry memory entry = Entry(KVFields);

            // 插入
            int256 count = tf.set("t_cms", cms_id, entry);
            if (count == 1) {
                // 成功
                ret_code = 0;
            } else {
                // 失败? 无权限或者其他错误
                ret_code = -2;
            }
        } else {
            // 账户已存在
            ret_code = -1;
        }

        emit RegisterEvent(ret_code, cms_id, cms_str);

        return ret_code;
    }
}
