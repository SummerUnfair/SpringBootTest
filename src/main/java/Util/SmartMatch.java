package Util;

//import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 此类主要调用——标准地址匹配接口——-获得城地坐标，百度经纬度，标准地址id，和标准地址
 * @author liwei   2019/01/08
 * (1)getStandard_id
 * (2)getStandard_Address
 * (3)String[] getCityXandY_arr
 * (4)String  getCityXandY_String
 * (5)getBaiDuShape
 *
 * 重要工具类
 */
public class SmartMatch {
	//获取日志器对象
		//static Logger log=Logger.getLogger(SmartMatch.class);
	/**
	 * (1)getStandard_id
	 * 通过非标地址获得标准地址id  1
	 * @param addr  非标地址
	 * @return  标准地址id
	 */
	public static String getStandard_id(String addr){
		String  address= addr.replaceAll(" ", "");
		String url = "http://10.7.74.41/addrselection/addressIndex/smartMatchAddress.do?addr_full=" + address;
		String jsonRes = null;
		try {
			jsonRes = HttpClientUtil.get(url);
		} catch (Exception e1) {
			String msg="SmartMatch的getStandard_id方法调用接口失败,原地址为=("
					+addr+")";
			//log.info(msg);
			e1.printStackTrace();
		}
		String standardId =null;
		if (jsonRes.contains("addr_full")) {
			JSONObject resJson = JSONObject.fromObject(jsonRes);
			Object data = resJson.get("data");
			JSONArray dataRes = JSONArray.fromObject(data);
			JSONObject jsObj = new JSONObject();
			for(Object obj : dataRes){
				jsObj = JSONObject.fromObject(obj);
			}
			JSONObject dataJson = JSONObject.fromObject(jsObj);
			standardId = dataJson.get("id").toString();
			}else {
				String msg="SmartMatch的getStandard_id方法未查询到数据,原地址为=("
						+addr+")";
			//	log.info(msg);
			}
		return standardId;
	}

	/**
	 * (2)getStandard_Address
	 * 通过非标地址获得标准地址
	 * @param addr  非标地址
	 * @return  标准地址
	 */
	public static String getStandard_Address(String addr){
		String  address= addr.replaceAll(" ", "");
		String url = "http://10.7.74.41/addrselection/addressIndex/smartMatchAddress.do?addr_full=" + address;
		String jsonRes = null;
		try {
			jsonRes = HttpClientUtil.get(url);
		} catch (Exception e1) {
			String msg="SmartMatch的getStandard_Address方法调用接口失败,原地址为=("
					+addr+")";
		//	log.info(msg);
			e1.printStackTrace();
		}
		String standardAddress =null;
		if (jsonRes.contains("addr_full")) {
			JSONObject resJson = JSONObject.fromObject(jsonRes);
			Object data = resJson.get("data");
			JSONArray dataRes = JSONArray.fromObject(data);
			JSONObject jsObj = new JSONObject();
			for(Object obj : dataRes){
				jsObj = JSONObject.fromObject(obj);
			}
			JSONObject dataJson = JSONObject.fromObject(jsObj);
			standardAddress = dataJson.get("addr_full").toString();

			}else {
				String msg="SmartMatch的getStandard_Address方法未查询到数据,原地址为=("
						+addr+")";
			//	log.info(msg);
			}
		return standardAddress;
	}




}
