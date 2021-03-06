package com.sgcc.pda.framelibrary.utils;

import com.intellij.openapi.util.Iconable;
import com.sgcc.pda.framelibrary.protocol698.apdu.data.IData;
import com.sgcc.pda.framelibrary.utils.TextUtils;
import org.jf.util.SparseArray;

import java.util.*;

/**
 * Created by Tsinling on 2019/1/29 16:15
 * Description: 错误管理类
 */
public class ErrorManager {

    private static final String UN_KNOWN_ERR = "未知错误";
    private static final String SUCCESS = "成功";


    public static void main(String[] args) {

        int safeUnit2ErrorCode = Integer.parseInt("000102", 16) + (0x02 << 24);
        System.out.println(Integer.toHexString(safeUnit2ErrorCode));
        System.out.println(getErrorMessage(safeUnit2ErrorCode));

    }

    private static String geterr(String safeUnit2ErrorMsg, String safeUnit1ErrorMsg) {
        String[] errorMsg = new String[]{
                "出现错误：请基于安全单元版本进行判断\n安全单元2.0：" + safeUnit2ErrorMsg + "\n安全单元1.0： " + safeUnit1ErrorMsg,
                safeUnit2ErrorMsg,
                safeUnit1ErrorMsg,
                UN_KNOWN_ERR
        };
        BitSet bitSet = new BitSet();
        boolean a = UN_KNOWN_ERR.equalsIgnoreCase(safeUnit2ErrorMsg);
        boolean b = UN_KNOWN_ERR.equalsIgnoreCase(safeUnit1ErrorMsg);
        System.out.println(a);
        System.out.println(b);
        bitSet.set(0, a);
        bitSet.set(1, b);
      /*  bitSet.set(0,true);
        bitSet.set(1,false);*/
        for (int i = 0; i < bitSet.toByteArray().length; i++) {
            int index = bitSet.toByteArray()[i];
            System.out.println(index);
        }


        //  return errorMsg[index];
        return "";
    }


    /**
     * 硬件交互错误类型  第一个字节 B3
     * <p>
     * ff 通用  可以标识 0x000000 - 0xfffffe 种错误   其中ff ffffff 表示未知错误：
     * 00 禁用，因为安全单元 传入进来的值 为 00 B2,B1,B0 其中B3 位置为 00， 其表示安全单元类型未被区分
     * 01 安全单元1.0  B2 表示 主功能标识，B1  第一个字节 B3.,B0 状态码   其中B2B1为FFFF时，表示通用错误。
     * 02 安全单元2.0  同上
     * 03 业务流程错误类型   可以标识 0x000000 - 0xffffff 种错误
     * 04 业务流程安全单元    同上
     * 05 业务流程电表   同上
     * 06 主站错误类型  同上
     * 07 P645错误类型   同上
     * 08 数据库错误类型  同上
     * 09 Resam错误类型   同上
     * 0A 安全单元收发错误类型
     * <p>
     * ... (可扩展：0x0B-0xFE)还可以扩展244 种主类型
     * FFFFFFFF "未知错误"
     */
    public enum ErrorType {
        // 通用错误 ff
        CommonReadError(0xff000001),
        CommonBufferError(0xff000002),
        CommonParamError(0xff000003),
        ComputeMD5Error(0xff000004),
        ComputeCrc32Error(0xff000005),
        GZipError(0xff000006),
        FileError(0xff000007),
        CommonDataError(0xff000008),
        SocketError(0xff000009),
        InfraError(0xff00000A),
        SafetyUnitError(0xff00000B),
        CommonWriteError(0xff00000C),
        CommonProtocolError(0xff00000D),


        // 安全单元错误类型： 01，02

        //业务流程错误类型 03

        BusinessRegisterInfoNull(0x03000001),
        BusinessOrderAddressNull(0x03000002),
        BusinessOrderPermissionNull(0x03000003),
        BusinessOrderParamsError(0x03000004),
        BusinessTaskParamsError(0x03000005),
        //业务流程安全单元 04
        BusinessSafeUnitSync(0x04000001),
        BusinessSafeUnitGetPermission(0x04000002),
        BusinessSafeUnitRegisterTask(0x04000003),
        BusinessSafeUnitRemoteAuth(0x04000004),
        BusinessSafeUnitGetRandom(0x04000005),
        BusinessSafeUnitInfraAuth(0x04000006),
        BusinessSafeUnitGetClockData(0x04000007),
        BusinessSafeUnitRegisterAuth(0x04000008),
        BusinessSafeUnitGetParamsSettingData(0x04000009),
        BusinessSafeUnitGetRemoteControlData(0x0400000a),
        //业务流程电表 05
        BusinessMeterNoNull(0x05000001),
        BusinessMeterParamsSetting(0x05000002),
        BusinessMeterRemoteAuth(0x05000003),
        BusinessMeterReadRechargeInfo(0x05000004),
        BusinessMeterRechargeInfoError(0x05000005),
        BusinessMeterCustomerIdNotEqual(0x05000006),
        BusinessMeterGetChargeData(0x05000007),
        BusinessMeterRechargeExecute(0x05000008),
        BusinessMeterInfraAuthRequest(0x05000009),
        BusinessMeterInfraAuthExecute(0x0500000a),
        BusinessMeterWriteClockSetting(0x0500000b),
        BusinessMeterReadFreezeDate(0x0500000c),
        BusinessMeterReadDayTimePeriodDate(0x0500000d),
        BusinessMeterReadTime(0x0500000e),
        BusinessMeterReadPowerSaveState(0x0500000f),
        BusinessMeterRemoteControlExecute(0x05000010),
        // 主站错误类型 06
        MasterStationFrameError(0x06000001),
        MasterStationCheckValueError(0x06000002),
        MasterStationParamError(0x06000003),
        MasterStationReceiveDataError(0x06000004),
        MasterStationBufferError(0x06000005),
        MasterStationConfigError(0x06000006),
        MasterStationSendError(0x06000007),
        MasterStationReceiveError(0x06000008),
        MasterStationFrameMatchError(0x06000009),

        // P645错误类型  07
        P645FrameError(0x07000001),
        P645CheckValueError(0x07000002),
        P645DataLengthError(0x07000003),
        P645SendNoData(0x07000004),
        P645ConfigError(0x07000005),
        P645SendError(0x07000006),
        P645ReceiveBufferError(0x07000007),
        P645ReceivedErrorValue(0x07000008),
        P645UpDownNotMatch(0x07000009),
        P645ReceivedDataError(0x0700000a),
        P645ErrorParam(0x0700000b),
        P645FrameMatchError(0x0700000c),
        // 数据库错误类型  08
        DatabaseNotFoundError(0x08000001),
        DatabaseInsertError(0x08000002),
        DatabaseUpdateError(0x08000003),
        DatabaseParserError(0x08000004),
        DatabaseNotReadyError(0x08000005),

        //Resam错误类型 09
        ResamSendDataError(0x09000001),
        ResamReceiveDataError(0x09000002),
        ResamEncryptEmptyDataError(0x09000003),
        ResamDecryptEmptyDataError(0x09000004),


        // 安全单元错误类型
        SafetyFrameError(0x0a000001),
        SafetyCheckValueError(0x0a000002),
        SafetyGetStatusError(0x0a000003),
        SafetyParamError(0x0a000004),
        SafetyBufferError(0x0a000005),
        SafetyConfigError(0x0a000006),
        SafetySendError(0x0a000007),
        SafetyReceiveError(0x0a000008),
        SafetyReceiveDataError(0x0a000009),
        SafetyUpdateFileError(0x0a00000a),
        SafetyFrameMatchError(0x0a00000b),
        // 未知错误
        UnKnowError(0xFFFFFFFF),;
        final int value;

        ErrorType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 错误信息集
     */
    private static final SparseArray<String> errorMessage = new SparseArray<>();

    static {
        //主站相关错误
        errorMessage.put(ErrorType.MasterStationFrameError.value, "主站通讯帧格式错误");
        errorMessage.put(ErrorType.MasterStationCheckValueError.value, "主站通讯帧校验错误");
        errorMessage.put(ErrorType.MasterStationParamError.value, "主站通讯参数错误");
        errorMessage.put(ErrorType.MasterStationReceiveDataError.value, "读取的返回的数据错误");
        errorMessage.put(ErrorType.MasterStationBufferError.value, "保存数据缓存区错误");
        errorMessage.put(ErrorType.MasterStationConfigError.value, "协议和帧结构定义为空");
        errorMessage.put(ErrorType.MasterStationSendError.value, "向主站发送数据错误");
        errorMessage.put(ErrorType.MasterStationReceiveError.value, "从主站获取数据错误");
        errorMessage.put(ErrorType.MasterStationFrameMatchError.value, "上下行帧不匹配");
        //安全单元相关错误
        errorMessage.put(ErrorType.SafetyFrameError.value, "安全单元通讯帧格式错误");
        errorMessage.put(ErrorType.SafetyCheckValueError.value, "安全单元通讯帧校验错误");
        errorMessage.put(ErrorType.SafetyGetStatusError.value, "安全单元下行帧返回了异常帧");
        errorMessage.put(ErrorType.SafetyParamError.value, "安全单元输入参数错误");
        errorMessage.put(ErrorType.SafetyBufferError.value, "安全单元接用于收返回的缓冲区错误");
        errorMessage.put(ErrorType.SafetyConfigError.value, "协议和帧结构定义为空");
        errorMessage.put(ErrorType.SafetySendError.value, "安全单元发送数据错误");
        errorMessage.put(ErrorType.SafetyReceiveError.value, "安全单元接收数据错误");
        errorMessage.put(ErrorType.SafetyReceiveDataError.value, "从安全单元接受到的数据错误");
        errorMessage.put(ErrorType.SafetyUpdateFileError.value, "安全单元升级文件错误");
        errorMessage.put(ErrorType.SafetyFrameMatchError.value, "上下行帧不匹配");
        //P645相关错误
        errorMessage.put(ErrorType.P645FrameError.value, "P645通讯帧格式错误");
        errorMessage.put(ErrorType.P645CheckValueError.value, "P645通讯帧校验错误");
        errorMessage.put(ErrorType.P645DataLengthError.value, "P645通讯帧数据长度错误");
        errorMessage.put(ErrorType.P645SendNoData.value, "P645待发送数据为空");
        errorMessage.put(ErrorType.P645ConfigError.value, "P645通讯参数设置错误");
        errorMessage.put(ErrorType.P645SendError.value, "P645数据发送错误");
        errorMessage.put(ErrorType.P645ReceiveBufferError.value, "用来接收P645返回数据的缓冲区错误");
        errorMessage.put(ErrorType.P645ReceivedErrorValue.value, "P645接收了一个包含异常信息的返回帧");
        errorMessage.put(ErrorType.P645UpDownNotMatch.value, "P645上下行帧不匹配");
        errorMessage.put(ErrorType.P645ReceivedDataError.value, "P645获取的数据域错误");
        errorMessage.put(ErrorType.P645ErrorParam.value, "P645命令输入参数错误");
        errorMessage.put(ErrorType.P645FrameMatchError.value, "上下行帧不匹配");
        //数据库操作错误
        errorMessage.put(ErrorType.DatabaseNotFoundError.value, "在数据库中没有查找到对应的记录");
        errorMessage.put(ErrorType.DatabaseInsertError.value, "向数据库中插入数据错误");
        errorMessage.put(ErrorType.DatabaseUpdateError.value, "更新数据库数据错误");
        errorMessage.put(ErrorType.DatabaseParserError.value, "数据格式错误");
        errorMessage.put(ErrorType.DatabaseNotReadyError.value, "没有数据库实例");

        /*RESAM错误*/
        errorMessage.put(ErrorType.ResamSendDataError.value, "resam发送数据错误");
        errorMessage.put(ErrorType.ResamReceiveDataError.value, "resam接收数据错误");
        errorMessage.put(ErrorType.ResamEncryptEmptyDataError.value, "resam加密数据传入的数据为空错误");
        errorMessage.put(ErrorType.ResamDecryptEmptyDataError.value, "resam解密数据传入的数据为空错误");
        // 通用错误类型
        errorMessage.put(ErrorType.CommonReadError.value, "读取数据错误");
        errorMessage.put(ErrorType.CommonBufferError.value, "返回值缓冲区为空");
        errorMessage.put(ErrorType.CommonParamError.value, "输入参数不合法");
        errorMessage.put(ErrorType.ComputeMD5Error.value, "计算MD5错误");
        errorMessage.put(ErrorType.ComputeCrc32Error.value, "计算CRC32错误");
        errorMessage.put(ErrorType.GZipError.value, "文件解压缩错误");
        errorMessage.put(ErrorType.FileError.value, "文件操作错误");
        errorMessage.put(ErrorType.CommonDataError.value, "数据错误");
        errorMessage.put(ErrorType.SocketError.value, "SOCKET操作错误");
        errorMessage.put(ErrorType.InfraError.value, "红外设备操作失败");
        errorMessage.put(ErrorType.SafetyUnitError.value, "安全单元设备操作失败");
        errorMessage.put(ErrorType.CommonWriteError.value, "数据写入错误");
        errorMessage.put(ErrorType.CommonProtocolError.value, "协议定义为空");
        //业务流程错误
        errorMessage.put(ErrorType.BusinessRegisterInfoNull.value, "本地数据库获取安全单元注册信息为空");
        errorMessage.put(ErrorType.BusinessOrderAddressNull.value, "工单表地址为空");
        errorMessage.put(ErrorType.BusinessOrderPermissionNull.value, "工单无权限信息");
        errorMessage.put(ErrorType.BusinessOrderParamsError.value, "工单参数有误");
        errorMessage.put(ErrorType.BusinessTaskParamsError.value, "工单执行时异步任务参数错误");

        errorMessage.put(ErrorType.BusinessSafeUnitSync.value, "安全单元同步失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetPermission.value, "安全单元获取操作员代码和权限失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRegisterTask.value, "安全单元注册任务标识失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRemoteAuth.value, "安全单元远程身份认证失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetRandom.value, "安全单元获取随机数失败");
        errorMessage.put(ErrorType.BusinessSafeUnitInfraAuth.value, "安全单元确认红外认证失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetClockData.value, "安全单元计算时钟设置数据失败");
        errorMessage.put(ErrorType.BusinessSafeUnitRegisterAuth.value, "安全单元注册权限失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetParamsSettingData.value, "安全单元获取参数设置任务数据失败");
        errorMessage.put(ErrorType.BusinessSafeUnitGetRemoteControlData.value, "安全单元获取远程控制命令数据失败");

        errorMessage.put(ErrorType.BusinessMeterNoNull.value, "电能表根据表地址读取表号失败，请检查表地址");
        errorMessage.put(ErrorType.BusinessMeterParamsSetting.value, "电能表参数设置失败");
        errorMessage.put(ErrorType.BusinessMeterRemoteAuth.value, "电能表远程身份认证失败");
        errorMessage.put(ErrorType.BusinessMeterReadRechargeInfo.value, "电能表查询充值信息失败");
        errorMessage.put(ErrorType.BusinessMeterRechargeInfoError.value, "电能表充值信息有误");
        errorMessage.put(ErrorType.BusinessMeterCustomerIdNotEqual.value, "电能表内读取客户编号与主站档案不一致");
        errorMessage.put(ErrorType.BusinessMeterGetChargeData.value, "电能表获取充值任务数据失败");
        errorMessage.put(ErrorType.BusinessMeterRechargeExecute.value, "电能表执行充值任务失败");
        errorMessage.put(ErrorType.BusinessMeterInfraAuthRequest.value, "电能表请求红外认证失败");
        errorMessage.put(ErrorType.BusinessMeterInfraAuthExecute.value, "电能表执行红外认证失败");
        errorMessage.put(ErrorType.BusinessMeterWriteClockSetting.value, "向电表发送时间设置命令失败");
        errorMessage.put(ErrorType.BusinessMeterReadFreezeDate.value, "电能表读取冻结数据失败");
        errorMessage.put(ErrorType.BusinessMeterReadDayTimePeriodDate.value, "电能表读取日时段数失败");
        errorMessage.put(ErrorType.BusinessMeterReadTime.value, "电能表读取时间失败");
        errorMessage.put(ErrorType.BusinessMeterReadPowerSaveState.value, "电能表读取保电状态失败");
        errorMessage.put(ErrorType.BusinessMeterRemoteControlExecute.value, "电能表执行电表控制失败");
    }


    private static SparseArray<String> safeUnitErrors = new SparseArray<>();
    private static HashMap<String, String> errorsMap = new HashMap<>();

    /**
     * 通用错误
     */
    static {
        //  B3     00 ：安全单元帧报文发送解析错误  01 安全单元1.0，02 安全单元2.0
        //  B2B1  ffff  安全单元 通用错误    B2: 主功能标识, B1：控制码
        //  B0    状态码：

        // 0x00000010  b3（01安全单元版本：01==1.0  02 == 2.0） b2（00，主功能标识），b1（02，控制码），b0（01，状态码）；
        // 0x01000201  b3（01安全单元版本：01==1.0  02 == 2.0） b2（00，主功能标识），b1（02，控制码），b0（01，状态码）；
        // 0x01ffffF7 安全单元版本 01： ffff 标识通用错误，F7 当前仅响应升级命令，只用于红外认证命

        safeUnitErrors.put(0x01000201, "保护码索引超范围");
        safeUnitErrors.put(0x01000202, "保护码索引已注册");
        safeUnitErrors.put(0x01000203, "传输 MAC 不匹配，即数据已改变");
        safeUnitErrors.put(0x01000204, "选文件目录失败");
        safeUnitErrors.put(0x01000205, "没有找到密钥");
        safeUnitErrors.put(0x010002F4, "写安全单元 e 方失败");// 协议上就是这个啥意思？
        /*保护码信息注销*/
        safeUnitErrors.put(0x01000301, "该保护码索引未注册");
        safeUnitErrors.put(0x01000302, "清除注册信息失败");
        /*查询保护码注册信息*/
        safeUnitErrors.put(0x01000401, "该任务标识未注册");
        /*计算保护码*/
        safeUnitErrors.put(0x01000501, "该保护码索引未注册");
        safeUnitErrors.put(0x010005F5, "解密随机数 A 失败或者计算 MAC 失败");
        /*验证保护码*/
        safeUnitErrors.put(0x01000601, "该任务标识未注册");
        safeUnitErrors.put(0x01000603, "保护码不匹配，数据已改变");
        /*下发操作权限*/
        safeUnitErrors.put(0x01000B01, "保护码信息未注册");
        safeUnitErrors.put(0x01000B02, "保护码信息超范围");
        safeUnitErrors.put(0x01000B03, "保护码不匹配，记录已改变");
        safeUnitErrors.put(0x01000B04, "无效的参数类型或一次注册的秘钥条数大于4");
        safeUnitErrors.put(0x01000B05, "没有找到密钥");
        safeUnitErrors.put(0x01000B11, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B12, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B13, "读业务卡密钥总条数失败");
        safeUnitErrors.put(0x01000B14, "读业务卡最近一次存放位置失败");
        safeUnitErrors.put(0x01000B15, "读业务卡密钥长度失败");
        safeUnitErrors.put(0x01000B16, "密钥长度为 0");
        safeUnitErrors.put(0x01000B17, "卡能支持的密钥条数太少");
        safeUnitErrors.put(0x01000B1B, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B1C, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B1D, "向业务卡写数据失败");
        safeUnitErrors.put(0x01000B1E, "向业务卡写数据失败");
        safeUnitErrors.put(0x01000B1F, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B20, "读业务卡数据失败");
        safeUnitErrors.put(0x01000B21, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000B23, "打开业务卡目录失败");
        safeUnitErrors.put(0x01000BF4, "写安全单元 E 方失败");
        safeUnitErrors.put(0x01000BF5, "打开业务卡目录失败");
        /*获取安全单元工作状态*/
        safeUnitErrors.put(0x01001001, "安全单元有问题");
        /*获取安全单元工作状态*/
        safeUnitErrors.put(0x01001101, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001105, "打开操作员卡目录失败");
        safeUnitErrors.put(0x010011F3, "操作员卡外部认证失败");
        /*验证操作员密码*/
        safeUnitErrors.put(0x01001201, "操作员卡未接入");
        safeUnitErrors.put(0x01001202, "密码错后跟 1 字节密码剩余尝试次数。");
        safeUnitErrors.put(0x01001203, "重试密码次数超允许值");
        safeUnitErrors.put(0x01001204, "操作员卡外部认证失败或计数器为 0");
        safeUnitErrors.put(0x01001205, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001206, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001207, "选文件失败");
        safeUnitErrors.put(0x01001208, "读取卡片序列号失败");
        safeUnitErrors.put(0x01001209, "密钥初始化失败");
        safeUnitErrors.put(0x01001210, "加密数据失败");
        safeUnitErrors.put(0x01001211, "取加密数据秘文失败");
        safeUnitErrors.put(0x01001212, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001213, "存储密码剩余尝试次数失败");
        safeUnitErrors.put(0x01001214, "存储最大密码剩余尝试次数失败");
        safeUnitErrors.put(0x010012FA, "验证操作员卡密码失败");
        safeUnitErrors.put(0x010012FB, "验证业务卡密码失败");
        /*修改操作员密码*/
        safeUnitErrors.put(0x01001301, "操作员卡未接入");
        safeUnitErrors.put(0x01001302, "密码错后跟 1 字节密码剩余尝试次数。");
        safeUnitErrors.put(0x01001303, "重试密码次数超允许值");
        safeUnitErrors.put(0x01001304, "新密码写入错误");
        safeUnitErrors.put(0x01001305, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001306, "读密码剩余次数失败");
        safeUnitErrors.put(0x01001307, "打开操作员卡目录失败");
        safeUnitErrors.put(0x01001308, "读取操作员卡序列号失败");
        safeUnitErrors.put(0x01001309, "密钥初始化失败");
        safeUnitErrors.put(0x01001310, "加密数据失败");
        safeUnitErrors.put(0x01001311, "取加密数据秘文失败");
        safeUnitErrors.put(0x01001312, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001313, "存储密码剩余尝试次数失败");
        safeUnitErrors.put(0x01001314, "写操作员卡数据失败");
        /*模块初始化*/
        safeUnitErrors.put(0x01001401, "安全单元故障，写 E 方或业务卡失败");
        /*安全单元与业务卡同步*/
        safeUnitErrors.put(0x010015F4, "写安全单元 E 方失败");
        safeUnitErrors.put(0x010015F5, "读取业务卡数据失败");
        /*卡片锁定*/
        safeUnitErrors.put(0x01001607, "打开操作员卡目录失败");
        safeUnitErrors.put(0x01001612, "读取操作员卡数据失败");
        safeUnitErrors.put(0x01001614, "写操作员卡数据失败");
        /*获取随机数*/
        safeUnitErrors.put(0x010021F4, "卡片类型不合法");
        /*加密随机数*/
        safeUnitErrors.put(0x010022F3, "打开操作员卡目录失败");
        safeUnitErrors.put(0x010022F4, "加密随机数失败");
        /*解密随机数*/
        safeUnitErrors.put(0x01002304, "数据长度不合法");
        safeUnitErrors.put(0x010023F4, "解密随机数失败");
        /*读取卡片序列号*/
        safeUnitErrors.put(0x01002404, "无效的卡片类型");
        safeUnitErrors.put(0x01002405, "打开文件目录失败");
        safeUnitErrors.put(0x010024F5, "获取随机数失败");
        /*查询索引目录计数次数*/
        safeUnitErrors.put(0x01002505, "打开文件目录失败或读取卡片数据失败");
        safeUnitErrors.put(0x010025F2, "卡片类型不合法");
        /*修改计数器*/
        safeUnitErrors.put(0x01002604, "卡片类型不合法");
        safeUnitErrors.put(0x01002605, "打开文件目录失败或修改卡片数据失败");
        /*读取当前对应表号的密钥信息*/
        safeUnitErrors.put(0x01002701, "该表号对应的密钥不存在");
        /*查询密钥索引状态*/
        safeUnitErrors.put(0x01002804, "密钥总条数不合法");
        safeUnitErrors.put(0x01002805, "打开文件目录失败或修改卡片数据失败");
        /*获取操作者信息*/
        safeUnitErrors.put(0x01002905, "打开文件目录失败或读取操作员卡片数据失败");
        /*计算传输 MAC*/
        safeUnitErrors.put(0x01002A05, "打开文件目录失败");
        safeUnitErrors.put(0x01002AF4, "计算 MAC 错误");
        /*验证计算传输 MAC*/
        safeUnitErrors.put(0x01002B02, "操作员卡未接入");
        safeUnitErrors.put(0x01002B05, "验证 MAC 错误");
        /*远程身份认证*/
        safeUnitErrors.put(0x01004104, "密钥状态不合法");
        safeUnitErrors.put(0x01004105, "打开文件目录失败或获取随机数失败或加密随机数失败");
        /*获取参数设置数据*/
        safeUnitErrors.put(0x01006701, "该保护码索引未注册");
        safeUnitErrors.put(0x01006702, "任务标识超范围");
        safeUnitErrors.put(0x01006703, "记录已改变，保护码错误");
        safeUnitErrors.put(0x01006704, "无效的参数类型");
        safeUnitErrors.put(0x01006705, "未找到密钥或选文件失败");
        safeUnitErrors.put(0x010067F2, "参数类型超范围");
        safeUnitErrors.put(0x010067F5, "解密随机数 A 失败");
        /*获取远程充值命令数据*/
        safeUnitErrors.put(0x01006801, "该保护码索引未注册");
        safeUnitErrors.put(0x01006802, "任务标识超范围");
        safeUnitErrors.put(0x01006803, "记录已改变，保护码错误");
        safeUnitErrors.put(0x01006804, "无效的参数类型");
        safeUnitErrors.put(0x01006805, "未找到密钥或选文件失败或计算 MAC 失败");
        safeUnitErrors.put(0x010068F5, "解密随机数 A 失败");
        /*获取远程控制命令数据*/
        safeUnitErrors.put(0x01006901, "该保护码索引未注册");
        safeUnitErrors.put(0x01006902, "任务标识超范围");
        safeUnitErrors.put(0x01006903, "记录已改变，保护码错误");
        safeUnitErrors.put(0x01006904, "无效的参数类型");
        safeUnitErrors.put(0x01006905, "未找到密钥或选文件失败或计算 MAC 失败");
        safeUnitErrors.put(0x010069F5, "解密随机数 A 失败");
        /*获取时钟设置命令数据*/
        safeUnitErrors.put(0x01006C01, "该保护码索引未注册");
        safeUnitErrors.put(0x01006C02, "任务标识超范围");
        safeUnitErrors.put(0x01006C03, "记录已改变，保护码错误");
        safeUnitErrors.put(0x01006C04, "无效的参数类型");
        safeUnitErrors.put(0x01006C05, "未找到密钥或选文件失败");
        safeUnitErrors.put(0x01006CF2, "参数类型超范围");
        safeUnitErrors.put(0x01006CF5, "解密随机数 A 失败");
        /*获取时钟设置命令数据*/
        safeUnitErrors.put(0x01007104, "无效的密钥类型");
        safeUnitErrors.put(0x01007105, "加密数据失败");
        safeUnitErrors.put(0x01007106, "选文件目录失败或随机数 1 的密文不匹配");
        /*获取时钟设置命令数据*/
        safeUnitErrors.put(0x01010201, "擦 ROM 失败");
        /*通用错误码*/
        safeUnitErrors.put(0x01ffffF1, "帧校验错误");
        safeUnitErrors.put(0x01ffffF2, "帧长度不符");
        safeUnitErrors.put(0x01ffffF3, "操作员权限不够");
        safeUnitErrors.put(0x01ffffF4, "安全模块错误‐  操作员卡操作失败");
        safeUnitErrors.put(0x01ffffF5, "安全模块错误‐业务卡操作错误");
        safeUnitErrors.put(0x01ffffF6, "安全单元与业务卡不同步");
        safeUnitErrors.put(0x01ffffF7, "当前仅响应升级命令，只用于红外认证命令");

        /*安全单元2.0版本*/
        /*获取安全单元信息*/
        safeUnitErrors.put(0x02000101, " 安全单元有问题 ");
        safeUnitErrors.put(0x02000102, " 获取C - ESAM序列号失败 ");
        safeUnitErrors.put(0x02000103, " 获取操作者代码失败 ");
        safeUnitErrors.put(0x02000104, " 获取权限和权限掩码失败 ");
        safeUnitErrors.put(0x02000105, " 获取操作者信息失败 ");
        safeUnitErrors.put(0x02000106, " 获取Y - ESAM信息失败 ");
        safeUnitErrors.put(0x02000107, " 获取转加密剩余次数失败 ");
        safeUnitErrors.put(0x02000108, " 获取密钥版本失败 ");
        safeUnitErrors.put(0x02000109, " 获取主站证书失败 ");
        safeUnitErrors.put(0x0200010a, " 获取终端证书失败 ");
        /*验证操作员密码*/
        safeUnitErrors.put(0x02000201, " 获取密码密文、最大密码尝试次数、剩余密码尝试次数失败 ");
        safeUnitErrors.put(0x02000202, " 最大密码尝试次数前后不相等 ");
        safeUnitErrors.put(0x02000203, " 剩余密码尝试次数前后不相等 ");
        safeUnitErrors.put(0x02000204, " 剩余密码次数为0 ");
        safeUnitErrors.put(0x02000205, " 解密密码密文失败 ");
        safeUnitErrors.put(0x02000206, " 密码尝试次数减1失败 ");
        safeUnitErrors.put(0x02000207, " 密码不一致 ");
        safeUnitErrors.put(0x02000208, " 恢复最大密码尝试次数失败 ");
        safeUnitErrors.put(0x02000209, " 获取YESAM序列号失败 ");
        safeUnitErrors.put(0x0200020a, " 获取YESAM序列号随机数失败 ");
        safeUnitErrors.put(0x0200020b, " 加密随机数失败 ");
        safeUnitErrors.put(0x0200020c, " 外部认证失败 ");
        /*修改操作员密码*/
        safeUnitErrors.put(0x02000301, " 获取密码密文、最大密码尝试次数、剩余密码尝试次数失败 ");
        safeUnitErrors.put(0x02000302, " 最大密码尝试次数前后不相等 ");
        safeUnitErrors.put(0x02000303, " 剩余密码尝试次数前后不相等 ");
        safeUnitErrors.put(0x02000304, " 剩余密码次数为0 ");
        safeUnitErrors.put(0x02000305, " 解密密码密文失败 ");
        safeUnitErrors.put(0x02000306, " 密码不一致返回剩余密码尝试次数 ");
        safeUnitErrors.put(0x02000307, " 恢复最大密码尝试次数失败 ");
        safeUnitErrors.put(0x02000308, " 加密明文密码失败 ");
        safeUnitErrors.put(0x02000309, " 修改密码失败 ");
        safeUnitErrors.put(0x0200030a, " 获取YESAM序列号失败 ");
        safeUnitErrors.put(0x0200030b, " 获取YESAM随机数失败 ");
        safeUnitErrors.put(0x0200030c, " 加密随机数失败 ");
        safeUnitErrors.put(0x0200030d, " 外部认证失败 ");
        safeUnitErrors.put(0x0200030e, " 解密修改后密码密文失败 ");
        safeUnitErrors.put(0x0200030f, " 新修改密码和输入密码不一致 ");
        /*锁定安全单元*/
        safeUnitErrors.put(0x02000401, " 外部认证失败 ");
        safeUnitErrors.put(0x02000402, " 清零失败 ");
        /*解锁安全单元*/
        safeUnitErrors.put(0x02000501, " 外部认证失败 ");
        safeUnitErrors.put(0x02000502, " 获取密码尝试次数失败 ");
        safeUnitErrors.put(0x02000503, " 修改最大密码尝试次数失败 ");
        safeUnitErrors.put(0x02000504, " 加密明文密码失败 ");
        safeUnitErrors.put(0x02000505, " 修改操作员密码失败 ");
        /*一次发行安全单元*/
        safeUnitErrors.put(0x02000601, " 一般错误 ");
        safeUnitErrors.put(0x02000602, " ESAM没有返回 ");
        safeUnitErrors.put(0x02000603, " ESAM返回错误码 ");
        /*二次发行安全单元*/
        safeUnitErrors.put(0x02000701, " 一般错误 ");
        safeUnitErrors.put(0x02000702, " ESAM没有返回0x55 ");
        safeUnitErrors.put(0x02000703, " ESAM返回错误码 ");
        /*存储关键数据*/
        safeUnitErrors.put(0x02000801, " 存储失败 ");
        /*读取关键数据*/
        safeUnitErrors.put(0x02000901, " 读取失败 ");
        /*透明转发ESAM指令*/
        safeUnitErrors.put(0x02000A01, " 一般错误 ");
        safeUnitErrors.put(0x02000A02, " ESAM没有返回0x55 ");
        safeUnitErrors.put(0x02000A03, " ESAM返回错误码 ");
        /*获取安全单元版本号*/
        safeUnitErrors.put(0x02000C01, " 获取失败 ");
        /*获取随机数*/
        safeUnitErrors.put(0x02010101, " 获取随机数失败 ");
        /*应用层身份认证（非对称密钥协商）*/
        safeUnitErrors.put(0x02010201, " 身份认证失败 ");
        /*应用层会话密钥加密算MAC*/
        safeUnitErrors.put(0x02010301, " 计算失败 ");
        /*应用层会话密钥解密验MAC*/
        safeUnitErrors.put(0x02010401, " 计算失败 ");
        /*转加密初始化*/
        safeUnitErrors.put(0x02010501, " 转加密初始化失败 ");
        /*置离线计数器*/
        safeUnitErrors.put(0x02010601, " 置离线计数失败 ");
        /*本地密钥计算MAC*/
        safeUnitErrors.put(0x02010701, " Y - ESAM计算失败 ");
        /*本地密钥验证MAC*/
        safeUnitErrors.put(0x02010801, " 验证失败 ");
        /*会话密钥计算MAC*/
        safeUnitErrors.put(0x02010905, " 打开文件目录失败 ");
        safeUnitErrors.put(0x020109F4, " 计算MAC错误 ");
        /*会话密钥验证MAC*/
        safeUnitErrors.put(0x02010A01, " 验证失败 ");
        /*电能表红外认证（13、698）*/
        safeUnitErrors.put(0x02020101, " 从Y - ESAM获取随机数密文失败 ");
        safeUnitErrors.put(0x02020102, " 从Y - ESAM获取的密文与随机数密文1不相等 ");
        safeUnitErrors.put(0x02020103, " 从Y - ESAM获取随机数密文2失败 ");
        /*远程身份认证（09、13）*/
        safeUnitErrors.put(0x02020201, " Y - ESAM认证失败 ");
        safeUnitErrors.put(0x02020202, " 获取随机数失败 ");
        /*电能表控制（09、13）*/
        safeUnitErrors.put(0x02020301, " 从Y - ESAM获取密文和MAC失败 ");
        /*电能表设参（09、13）*/
        safeUnitErrors.put(0x02020401, " 数据标识不对 ");
        safeUnitErrors.put(0x02020402, " Y - ESAM一类设参失败 ");
        safeUnitErrors.put(0x02020403, " Y - ESAM二类设参失败 ");
        safeUnitErrors.put(0x02020404, " 参数类型不对 ");
        /*电能表校时（09、13）*/
        safeUnitErrors.put(0x02020501, " 数据标识不对 ");
        safeUnitErrors.put(0x02020502, " 数据标识01 Y -ESAM获取密文 + MAC失败 ");
        safeUnitErrors.put(0x02020503, " 数据标识02 Y -ESAM获取密文 + MAC失败 ");
        safeUnitErrors.put(0x02020504, " 数据标识0c Y -ESAM获取密文 + MAC失败 ");
        /*电能表密钥更新(09)*/
        safeUnitErrors.put(0x02020604, " 密钥状态不合法 ");
        safeUnitErrors.put(0x02020605, " 打开文件目录失败或获取随机数失败或加密随机数失败 ");
        /*电能表密钥更新(13)*/
        safeUnitErrors.put(0x02020701, " 解密密文失败 ");
        safeUnitErrors.put(0x02020702, " 计算密钥包MAC 失败 ");
        safeUnitErrors.put(0x02020703, " 计算密钥包MAC 失败 ");
        safeUnitErrors.put(0x02020704, " 计算密钥包MAC 失败 ");
        safeUnitErrors.put(0x02020705, " 计算密钥包MAC 失败 ");
        safeUnitErrors.put(0x02020706, " 计算密钥包MAC 失败 ");
        /*电能表开户充值（09、13）*/
        safeUnitErrors.put(0x02020804, " 密钥状态不合法 ");
        safeUnitErrors.put(0x02020805, " 打开文件目录失败或获取随机数失败或加密随机数失败 ");
        /*698电能表会话协商*/
        safeUnitErrors.put(0x02020901, " 从Y - ESAM协商失败 ");
        /*698电能表会话协商验证*/
        safeUnitErrors.put(0x02020A01, " 从Y - ESAM验证失败 ");
        /*698电能表安全数据生成*/
        safeUnitErrors.put(0x02020b01, " 安全模式字不对 ");
        safeUnitErrors.put(0x02020b02, " 验证保护码失败 ");
        safeUnitErrors.put(0x02020b03, " Y - ESAM二层加密失败 ");
        safeUnitErrors.put(0x02020b04, " Y - ESAM的一层加密失败 ");
        safeUnitErrors.put(0x02020b05, " Y - ESAM获取随机数失败 ");
        /*698电能表安全传输解密*/
        safeUnitErrors.put(0x02020C01, " RESPONSE不对 ");
        safeUnitErrors.put(0x02020C02, " Y - ESAM解密明文 + MAC失败 ");
        safeUnitErrors.put(0x02020C03, " Y - ESAM解密密文失败 ");
        safeUnitErrors.put(0x02020C04, " Y - ESAM解密密文 + MAC失败 ");
        safeUnitErrors.put(0x02020C05, " 含数据验证信息数据不对 ");
        /*698电能表抄读数据验证*/
        safeUnitErrors.put(0x02020D04, " 密钥状态不合法 ");
        safeUnitErrors.put(0x02020D05, " 打开文件目录失败或获取随机数失败或加密随机数失败 ");
        /*698电能表抄读数据验证*/
        safeUnitErrors.put(0x02020E04, " Y - ESAM验证MAC失败 ");
        /*698电能表安全传输解密*/
        safeUnitErrors.put(0x02020F01, " 安全模式字不对 ");
        safeUnitErrors.put(0x02020F02, " 验证保护码失败 ");
        safeUnitErrors.put(0x02020F03, " Y - ESAM二层加密失败 ");
        safeUnitErrors.put(0x02020F04, " Y - ESAM的一层加密失败 ");
        safeUnitErrors.put(0x02020F05, " Y - ESAM获取随机数失败 ");
        /*698电能表安全传输解密*/
        safeUnitErrors.put(0x02021001, " 控制数据第一个字节不对 ");
        safeUnitErrors.put(0x02021002, " 控制数据第二个字节不对 ");
        safeUnitErrors.put(0x02021003, " 验证MAC失败 ");
        safeUnitErrors.put(0x02021004, " 从Y - ESAM获取密文和MAC失败 ");
        /*农排表设参*/
        safeUnitErrors.put(0x02021101, " 数据标识不对 ");
        safeUnitErrors.put(0x02021102, " Y - ESAM一类设参失败 ");
        safeUnitErrors.put(0x02021103, " Y - ESAM二类设参验证MAC失败 ");
        safeUnitErrors.put(0x02021104, " 二类设参生成密文 + MAC失败 ");
        safeUnitErrors.put(0x02021105, " 参数类型错误 ");
        /*农排表校时*/
        safeUnitErrors.put(0x02021201, " 数据标识不对 ");
        safeUnitErrors.put(0x02021202, " Y - ESAM获取密文 + MAC失败 ");
        safeUnitErrors.put(0x02021203, " 验证下发的MAC失败 ");
        /*农排表开户充值*/
        safeUnitErrors.put(0x02021301, " 生成密文MAC失败 ");
        /*链路层非对称密钥协商*/
        safeUnitErrors.put(0x02030101, " YESAM身份认证失败 ");
        /*链路层会话密钥加密计算MAC*/
        safeUnitErrors.put(0x02030201, " YESAM算MAC失败 ");
        /*链路层会话密钥解密验证MAC*/
        safeUnitErrors.put(0x02030301, " YESAM解密验MAC失败 ");
        /*链路层会话密钥计算MAC*/
        safeUnitErrors.put(0x02030404, " YESAM解密验MAC失败 ");
        safeUnitErrors.put(0x020304F4, " YESAM解密验MAC失败 ");
        /*链路层会话密钥验证MAC*/
        safeUnitErrors.put(0x02030504, " 数据长度不合法 ");
        safeUnitErrors.put(0x020305F4, " 解密随机数失败 ");
        /*电子封印读认证验证token1*/
        safeUnitErrors.put(0x02040101, " Y - ESAM电子标签认证失败 ");
        /*电子封印读认证验证token2*/
        safeUnitErrors.put(0x02040201, " Y - ESAM电子标签认证失败 ");
        /*加密数据地址（读）*/
        safeUnitErrors.put(0x02040301, " Y - ESAM加密数据地址失败 ");
        /*解密读回数据*/
        safeUnitErrors.put(0x02040401, " Y - ESAM解密回读数据失败 ");
        /*电子标签读认证生成token1*/
        safeUnitErrors.put(0x02050101, " Y - ESAM生成明文数据失败 ");
        safeUnitErrors.put(0x02050102, " Y - ESAM生成Token1失败 ");
        /*电子标签读认证验证token2*/
        safeUnitErrors.put(0x02050201, " Y - ESAM验证Token2失败 ");
        /*电子标签MAC验证*/
        safeUnitErrors.put(0x02050305, " 打开文件目录失败 ");
        safeUnitErrors.put(0x020503F4, " 计算MAC错误 ");
        /*电子标签解密*/
        safeUnitErrors.put(0x02050401, " YESAM解密失败 ");
        /*外设密钥协商*/
        safeUnitErrors.put(0x02060101, " 密钥协商失败 ");
        /*外设密钥协商确认*/
        safeUnitErrors.put(0x02060201, " 密钥协商确认失败 ");
        /*外设密钥协商确认*/
        safeUnitErrors.put(0x02060301, " 会话密钥加密计算失败 ");
        /*会话密钥解密验证MAC*/
        safeUnitErrors.put(0x02060401, " 会话密钥解密验证失败 ");
        /*会话密钥计算MAC*/
        safeUnitErrors.put(0x02060501, " 会话密钥计算失败 ");
        /*会话密钥验证MAC*/
        safeUnitErrors.put(0x02060601, " 会话密钥验证失败 ");
        /*升级命令1*/
        safeUnitErrors.put(0x02FE0101, " 擦ROM失败 ");
        /*通用错误码*/
        safeUnitErrors.put(0x02ffffF1, " 帧校验错误 ");
        safeUnitErrors.put(0x02ffffF2, " 帧结束码不符 ");
        safeUnitErrors.put(0x02ffffF3, " 帧长度超限 ");
        safeUnitErrors.put(0x02ffffF4, " 数据域长度超限 ");


        errorsMap.put("9000", "成功");
        errorsMap.put("6D00", "INS不支持");
        errorsMap.put("6E00", "CLA不支持");
        errorsMap.put("6A86", "P1P2不正确");
        errorsMap.put("6A82", "LC长度错误");
        errorsMap.put("6A81", "LE超长");
        errorsMap.put("6A80", "数据域错误（常见情景：权限数据有误，会话密钥不匹配，解码错误。）");
        errorsMap.put("6985", "使用条件不满足");
        errorsMap.put("6581", "存储器故障");
        errorsMap.put("6988", "计算错误");
        errorsMap.put("6989", "MAC错误");
        errorsMap.put("6980", "密钥不存在");
        errorsMap.put("6981", "文件不存在");
        errorsMap.put("6982", "会话未建立");
        errorsMap.put("6910", "置离线计数器");
    }


    /**
     * 错误码
     *
     * @param errorCode 主功能标识,功能码，状态码
     * @return
     */
    private static String getSafeUnitError(int errorCode) {
        if (errorCode >= 0x01000000 && errorCode < 0x03000000) { // 在01或者02范围内
            return safeUnitErrors.get(errorCode, getCommonError(errorCode));
        } else if (errorCode < 0x01000000) { // 此处没有分出1.0，2.0
            // 此处errorcode为******  拼接全错误码， 安全单元2.0 为 02******， 安全单元1.0 为01******
            int safeUnit2ErrorCode = errorCode + (0x02 << 24);
            int safeUnit1ErrorCode = errorCode + (0x01 << 24);
            String safeUnit2ErrorMsg = safeUnitErrors.get(safeUnit2ErrorCode, getCommonError(safeUnit2ErrorCode));
            String safeUnit1ErrorMsg = safeUnitErrors.get(safeUnit1ErrorCode, getCommonError(safeUnit1ErrorCode));
            return getErrorMsg(safeUnit2ErrorMsg, safeUnit1ErrorMsg);
        } else {
            return UN_KNOWN_ERR;
        }

    }

    private static String getErrorMsg(String safeUnit2ErrorMsg, String safeUnit1ErrorMsg) {

        if (!UN_KNOWN_ERR.equalsIgnoreCase(safeUnit2ErrorMsg) && UN_KNOWN_ERR.equalsIgnoreCase(safeUnit1ErrorMsg)) {
            return safeUnit2ErrorMsg;
        }
        if (UN_KNOWN_ERR.equalsIgnoreCase(safeUnit2ErrorMsg) && !UN_KNOWN_ERR.equalsIgnoreCase(safeUnit1ErrorMsg)) {
            return safeUnit1ErrorMsg;
        }
        if (!UN_KNOWN_ERR.equalsIgnoreCase(safeUnit2ErrorMsg) && !UN_KNOWN_ERR.equalsIgnoreCase(safeUnit1ErrorMsg)) {
            return "出现错误：请基于安全单元版本进行判断\n安全单元2.0：" + safeUnit2ErrorMsg + "\n安全单元1.0： " + safeUnit1ErrorMsg;
        }
        // 若安全单元1.0 和2.0 都获取不到对应错误，则返回未知错误。
       /* if (UN_KNOWN_ERR.equalsIgnoreCase(safeUnit2ErrorMsg) && UN_KNOWN_ERR.equalsIgnoreCase(safeUnit1ErrorMsg)){
            return UN_KNOWN_ERR;
        }*/
        return UN_KNOWN_ERR;
    }

    /**
     * @param errorCode    错误码
     * @param safeUnitType 安全单元类型  01 安全单元1.0，  02 安全单元2.0
     * @return
     */
    private static String getSafeUnitError(int errorCode, int safeUnitType) {
        int safeUnitErrorCode = errorCode + (safeUnitType << 24);
        return safeUnitErrors.get(safeUnitErrorCode, getCommonError(safeUnitErrorCode));
    }

    private static String getCommonError(int error) {
        String errorHex = String.format("%08x", error);
        String mainType = errorHex.substring(0, 2);
        String statusCode = errorHex.substring(6, 8);

        if ("01".equalsIgnoreCase(mainType) || "02".equalsIgnoreCase(mainType)) {
            errorHex = mainType + "FFFF" + statusCode;
            System.out.println(errorHex);
            int code;
            try {
                code = Integer.parseInt(errorHex, 16);
            } catch (Exception e) {
                code = 0xffffffff;
            }
            System.out.println(code);
            System.out.println(code);
            return safeUnitErrors.get(code, UN_KNOWN_ERR);
        }
        return UN_KNOWN_ERR;
    }

    /**
     * 安全单元 返回的2字节错误 如  6a80
     *
     * @param data 数据域（安全单元有时会返回2字节的 错误标识 如 6a80）
     * @return
     */
    public static String getErrorMessage(String data) {
        String errMsg = errorsMap.get(data);
        return TextUtils.isEmpty(errMsg) ? UN_KNOWN_ERR : errMsg;
    }

    /**
     * 安全单元
     *
     * @param error 错误码
     * @param data  返回的2字节错误 如  6a80
     * @return
     */
    public static String getErrorMessage(int error, String data) {
        StringBuilder err = new StringBuilder(getSafeUnitError(error));
        String errMsg = errorsMap.get(data);
        errMsg = TextUtils.isEmpty(errMsg) ? "" : ": " + errMsg;
        err.append(errMsg);
        return err.toString();
    }

    /**
     * 获取错误文字说明
     *
     * @param error 具体错误类型
     * @return 错误字符串，多个错误以":"隔开
     */
    public static String getErrorMessage(int error) {
        //System.out.println(Integer.toHexString(error));
        if (error == 0) return SUCCESS;
        return errorMessage.get(error, getSafeUnitError(error));
    }

    public static String getErrorMessage(int error, int safeUnitType) {
        if (error == 0) return SUCCESS;
        return errorMessage.get(error, getSafeUnitError(error, safeUnitType));
    }
}
