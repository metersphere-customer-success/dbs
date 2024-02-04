package metersphere.controller;


import metersphere.Utils.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/sql")
public class DatabaseServerController {

    @CrossOrigin
    @GetMapping("/{dataBaseType}/{dataBaseSettingName}/{sql}")
    public String DatabaseServer(@PathVariable String dataBaseType,@PathVariable String dataBaseSettingName, @PathVariable String sql) {
        if(dataBaseType == null || "".equals(dataBaseType)) return "dataBaseType 必填";
        if(dataBaseSettingName == null || "".equals(dataBaseSettingName)) return "dataBaseSettingName 必填";
        if(sql == null || "".equals(sql)) return "sql 必填";
        dataBaseType = dataBaseType.toLowerCase();
        DatabaseUtils databaseUtils = null;
        switch (dataBaseType){
            case "mysql":
                databaseUtils = new MysqlUtils(PropertiesUtils.getInst().getValue(dataBaseSettingName+".url"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".username"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".password"));
                break;
            case "orcale":
                databaseUtils = new OrcaleUtils(PropertiesUtils.getInst().getValue(dataBaseSettingName+".url"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".username"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".password"));
                break;
            case "sqlserver":
                databaseUtils = new SqlServerUtils(PropertiesUtils.getInst().getValue(dataBaseSettingName+".url"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".username"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".password"));
                break;
            case "postgresql":
                databaseUtils = new PostgreSqllUtils(PropertiesUtils.getInst().getValue(dataBaseSettingName+".url"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".username"),
                        PropertiesUtils.getInst().getValue(dataBaseSettingName+".password"));
                break;
            default:
                return "不支持 "+dataBaseType+" 类型数据库";
        }
        String response = requestDatabaseBySql(databaseUtils, sql);
        return response;
    }

    public String requestDatabaseBySql(DatabaseUtils databaseUtils,String sql){
        String[] sqlArray = sql.split(" ");
        String str = "";
        if(sqlArray[0].toLowerCase().equals("select")){
            str = databaseUtils.selectListBySQL(sql);
        }else{
            str = databaseUtils.updateSQL(sql);
        }
        return str;
    }

    @GetMapping("getPropertiesKeyAll")
    public String DatabaseServer() {
        return PropertiesUtils.getInst().getKeyAll();
    }

    @GetMapping("getPropertiesValueByKey")
    public String DatabaseServer(@PathVariable String key) {
        return PropertiesUtils.getInst().getValue(key);
    }


}
