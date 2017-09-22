package com.gtzn.modules.base.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 各常量类的集合
 * 任何情况下不得实现该接口
 * Created by fusu on 2016/12/22.
 */
public interface Constant {

    /**
     * 系统各模块名称枚举
     */
    enum ModuleType {
        BASE("基础"), LOGIN_OUT("系统登录退出"), PASSWORD_MANAGE("密码管理"), LOCATION("位置管理"),
        MENU_MANAGE("菜单管理"), DELIVER_DEVICE_CONFIG("交付机配置"), DICT_MANAGE("码表管理"),
        BORROW_PARAM("借阅参数"), DOOR_MANAGE("门禁管理"),

        FILE_MANAGE("档案管理"), NEW_FILE("新建档案"), FILE_INFO_IMPORT("信息导入"),

        TRANSFER("移交"), TRANSFER_LIST("移交清单"), TRANSFER_RECEIVE("移交接收"), FAST_TRANSFER("快捷移交"),
        TRANSFER_AUDIT("作业监督审核"), TRANSFER_FIRST_AUDIT("移交初审"), TRANSFER_LAST_AUDIT("移交审核"),

        BORROW("借阅"), BORROW_REQUEST_FORM("借阅申请单"), BORROW_AUTH("借阅授权"), BORROW_OUT("借阅出库"),
        BORROW_RETURN("借阅归还"), BORROW_DELIVER("借阅交付"), BORROW_RECALL("借阅催还"), BORROW_FILE("档案借阅"),

        INVENTORY("盘点"),PLANINVENTORY("计划盘点"),
        DESTROY("销毁"),
        RFID_MAKEUP("标签补录"),

        TAKE_FILE_LIST("取档清单"),
        SETTLE_DATA("结清资料"),
        ARCHIVE_MANAGE("要件管理"), WARRANT_MANAGE("权证管理");

        private String name;

        ModuleType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 各操作类型名称枚举
     */
    enum OperationType {
        SAVE("新增"), DELETE("删除"), UPDATE("修改"), FIND("查找"), LOGIN("系统登录"), LOGOUT("系统退出"),
        IMPORT("导入"),SYNC("同步"),
        destroyAdd("加入鉴定清册"),
        destroyHand("提交鉴定清册"),
        destroyApprasiral("鉴定档案"),
        destroyRecall("申请撤回"),
        destroyRecallYes("同意撤回"),
        destroyYes("同意销毁"),
        DISABLE("禁用"), SUBMIT("提交"),
    	PRINT("打印");
        private String name;

        OperationType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 请求设备类型名称枚举
     */
    enum DeviceType {
        PC("PC_浏览器"), ANDROID("android设备"), MOBILE_WORKSTATION("移动工作站")
    	
      
    	;
        private String name;

        DeviceType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 档案轨迹的状态
     */
    enum StatusType {
        returning("已归还待确认"), waitdelivery("待交付"), returned("已归还"), waitout("待出库"),
        inlibrary("在库"), extraction("提取"), readyshelf("待上架"), readyout("待出库"),
        outlibrary("出库"),
        initialization("初始化"), transfersubmit("提交"), check_pass("审核通过"), check_back("审核退回"), store_receive("已接收"),
        
        
        
        
    	destroyAdd("加入鉴定清册"),
    	destroySubmit("提交审批"),
    	destroyOfficePass("办公室审批通过"),
    	destroyOfficeBack("办公室审批退回"),
    	destroyOfficeSubmi("办公室提交审批"),
    	destroyEditSubmit("整改后提交审批"),
    	destroyApprasiral("已鉴定"),
    	destroyApprasiralPass("鉴定通过"),
    	destroyApprasiralBack("鉴定退回"),
    	destroyApprasiralSubmi("鉴定完成提交审批"),
    	destroyInformgt("信贷负责人鉴定档案"),
    	destroyInformgtPass("信贷中心审批通过"),
    	destroyInformgtBack("信贷中心审批退回"),
    	destroyInformgtSubmit("信贷中心提交审批"),
    	destroyOffice("办公室负责人鉴定档案"),
    	destroyBankPass("分管行长审批通过"),
    	destroyBankBack("分管行长审批退回"),
    	destroyBankSubmit("分管行长提交审批"),
    	destroyMan1Pass("销毁确认人1审批通过"),
    	destroyMan1Back("销毁确认人1审批退回"),
    	destroyMan1Submit("销毁确认人1完成确认"),
    	destroyMan2Pass("销毁确认人2审批通过"),
    	destroyMan2Back("销毁确认人2审批退回"),
    	destroyMan2Submit("销毁确认人2完成确认"),
    	destroyRecallPass("同意撤回"),
    	destroyRecallBack("不同意撤回"),
    	destroyNo("待返库"),
    	destroyYes("待销毁")
    	;
        private String name;

        StatusType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 档案状态
     */
    enum FileStatus {
        INITIALIZATION("initialization"), READYSHELF("readyshelf"), INLIBRARY("inlibrary"),
        READYOUT("readyout"), EXTRACTION("extraction"), OUTLIBRARY("outlibrary"),
        WAITDESTORY("waitdestory"), DESTORY("destory");

        private String name;

        FileStatus(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * 档案类型
     */
    enum FileType implements BaseEnum<FileType, Integer> {
        WARRANT("权证", 0), ARCHIVE("要件", 1), ATTACH("附加资料", 2);

        private String name;
        private Integer value;

        FileType(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @JsonValue
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getEnumName() {
            return this.name();
        }

        @Override
        public Integer getValue() {
            return value;
        }

    }

    /**
     * 业务类型
     */
    enum BusinessType implements BaseEnum<BusinessType, Integer>{
        P_LOAN("个贷", 0), CARD_INSTALLMENT("卡分期", 1), ALL("全部", -1);

        private String name;
        private Integer value;

        BusinessType(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @JsonValue
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getEnumName() {
            return this.name();
        }

        @Override
        public Integer getValue() {
            return value;
        }


    }

    /**
     * 移交单内档案状态
     */
    enum TransferFileStatus implements BaseEnum<TransferFileStatus, Integer>{
        INIT("初始化", 0), TO_BE_AUDIT("贷后待审核", 10), AUDIT_BACK("贷后审核退回", 11),
        TO_BE_RECEIVE("待接收", 20), RECEIVE_BACK("接收退回", 21), TO_BE_CHECK("待审核", 30),
        CHECK_BACK("审核退回", 31), TO_BE_LIB("库管待接收", 40), TO_BE_LIB_PASS("库管接收完成", 42);

        private String name;
        private Integer value;

        TransferFileStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @JsonValue
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getEnumName() {
            return this.name();
        }

        @Override
        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("value", value)
                    .toString();
        }
    }

    /**
     * 移交单状态
     */
    enum TransferStatus implements BaseEnum<TransferStatus, Integer>{
        INIT("待提交", 0), UNFINISHED("未完成", 1), FINISHED("已完成", 2);

        private String name;
        private Integer value;

        TransferStatus(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @JsonValue
        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getEnumName() {
            return this.name();
        }

        @Override
        public Integer getValue() {
            return value;
        }

    }
    
    /**
     * 工作流 key
     */
    enum WorkflowProcDefKey {
    	
        destroy("销毁", "destroy");

        private String name;
        private String key;
        
		private WorkflowProcDefKey(String name, String key) {
			this.name = name;
			this.key = key;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}

    }
    

  //档案利用借阅类型
    public Map<String,String> workflow_type = new TreeMap<String,String>(){{
    	put("annualenter","年度进馆");
    	put("archivesborrow","档案借阅(电子)");
    	put("archivesborrowst","档案借阅(实体)");
		put("archivesuse","利用登记");
		put("archivesresearch","档案编研");
		put("archivesrescue","档案抢救");
		put("archivesdonate","档案捐赠");
		put("archivesdeposite","档案寄存");
		put("archivesexchange","档案交换");
		put("archivespurchase","档案征购");
		put("delayidentification","档案延期鉴定");
		put("denseidentification","档案密级鉴定");
		put("destroyidentification","档案销毁鉴定");
		put("endangeridentification","档案濒危鉴定");
		put("lossidentification","档案遗失鉴定");
		put("planidentification","档案划控鉴定");
		put("openidentification","档案开放鉴定");
		put("archivesuselook","利用登记查看申请");
		put("archivesuseprint","利用登记打印申请");
		put("archivesusecall","利用登记调档申请");
        }};
        
      //单子状态
        public static Map<String,Object> base_status = new TreeMap<String,Object>(){
        	{
        		put("","全部");
        		put("0","未提交");
        		put("1","审批中");
        		put("2","已通过");
        		put("3","未通过");
        	}
        };
        
      //工作进度类型
        public static Map<String,Object> work_type = new TreeMap<String,Object>(){
        	{
        		put("","全部");
        		put("利用","档案利用");
        		put("借阅","档案借阅");
        		put("濒危","濒危鉴定");
        		put("划控","划控鉴定");
        		put("开放","开放鉴定");
        		put("密级","密级鉴定");
        		put("销毁","销毁鉴定");
        		put("延期","延期鉴定");
        		put("遗失","遗失鉴定");
        		put("捐赠","档案捐赠");
        		put("寄存","档案寄存");
        		put("交换","档案交换");
        		put("征购","档案征购");
        		put("移交","档案移交");
        	}
        };
        
      //工作任务状态
        public static Map<String,Object> work_state = new TreeMap<String,Object>(){
        	{
        		put("0","待审批");
        		put("1","已同意");
        		put("2","已拒绝");
        		put("3","转审批");
        		put("4","已完成");
        	}
        };
        
      //档案借阅目的
        public static Map<String,Object> borrow_purpose = new TreeMap<String,Object>(){
        	{
        		put("0","学术研究");
        		put("1","编史修志");
        		put("2","工作考察");
        		put("3","经济建设");
        		put("4","宣传教育");
        		put("5","其它");
        	}
        };
       
      //档案征集类型
        public static String[] collect_type = {"捐赠","寄存","交换","征购"};
      //档案鉴定类型
        public static String[] auth_type = {"濒危","划控","开放","密级","销毁","延期","遗失"};
      //档案征集来源类型
        public static String[] collect_source_type = {"个人","机构"};
      //档案保管期限
        public static Map<String,Object> archive_period = new TreeMap<String,Object>(){
        	{
        		put("w","5年");
        		put("dq","短期（10年）");
        		put("sw","15年");
        		put("ew","25年");
        		put("cq","长期（30年）");
        		put("yj","永久");
        	}
        };
    
}
