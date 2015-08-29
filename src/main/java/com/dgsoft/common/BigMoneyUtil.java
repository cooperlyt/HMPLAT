package com.dgsoft.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.*;

public final class BigMoneyUtil {
	
	//总体思路：
	//对数字进行分级处理，级长为4 
	//对分级后的每级分别处理，处理后得到字符串相连 
	//如：123456=12|3456 
	//第二级：12=壹拾贰 + “万” 
	//第一级：3456 =叁千肆百伍拾陆 + “”  
	private double amount = 0.0D;
	private static final String NUM = "零壹贰叁肆伍陆柒捌玖";
	private static final String UNIT = "仟佰拾个";
	private static final String GRADEUNIT = "仟万亿兆";
	private static final String DOTUNIT = "角分厘";
	private static final int GRADE = 4;
	private static final NumberFormat nf = new DecimalFormat("#0.###");

	public BigMoneyUtil(double amount) {
		this.amount = amount;
	}
	public BigMoneyUtil() {
	}
	public String toBigAmt() {
		return getBigMoney(this.amount);
	}
	
	public static String getBigArea(double amount) {
		String amt = nf.format(amount);
		String dotPart = "";
		String intPart = "";
		int dotPos;
		if ((dotPos = amt.indexOf('.')) != -1) {
			intPart = amt.substring(0, dotPos);
			dotPart = amt.substring(dotPos + 1);
		} else {
			intPart = amt;
		}
		if (intPart.length() > 16)
			throw new InternalError("The amount is too big.");
		String intBig = intToBig(intPart);
		String dotBig = decimalToBig(dotPart);
		if ((dotBig.length() == 0) && (intBig.length() != 0)) {
			return intBig + "";
		} else if ((dotBig.length() == 0) && (intBig.length() == 0)) {
			return intBig + "零";
		} else if ((dotBig.length() != 0) && (intBig.length() != 0)) {
			return intBig + "点" + dotBig;
		} else {
			return "零点"+dotBig;
		}
	}
	private static String decimalToBig(String dotPart) {
		String strRet = "";
		for (int i = 0; i < dotPart.length() && i < 3; i++) {
			int num;
			num = Integer.parseInt(dotPart.substring(i, i + 1));
			strRet += NUM.substring(num, num + 1);
		}
		return strRet;
	}
	
	public static String getBigMoney(double amount) {
		String amt = nf.format(amount);
		String dotPart = "";
		String intPart = "";
		int dotPos;
		if ((dotPos = amt.indexOf('.')) != -1) {
			intPart = amt.substring(0, dotPos);
			dotPart = amt.substring(dotPos + 1);
		} else {
			intPart = amt;
		}
		if (intPart.length() > 16)
			throw new InternalError("The amount is too big.");
		String intBig = intToBig(intPart);
		String dotBig = dotToBig(dotPart);
		if ((dotBig.length() == 0) && (intBig.length() != 0)) {
			return intBig + "元整";
		} else if ((dotBig.length() == 0) && (intBig.length() == 0)) {
			return intBig + "零元";
		} else if ((dotBig.length() != 0) && (intBig.length() != 0)) {
			return intBig + "元" + dotBig;
		} else {
			return dotBig;
		}
	}

	private static String dotToBig(String dotPart) {
		String strRet = "";
		for (int i = 0; i < dotPart.length() && i < 3; i++) {
			int num;
			if ((num = Integer.parseInt(dotPart.substring(i, i + 1))) != 0)
				strRet += NUM.substring(num, num + 1)
						+ DOTUNIT.substring(i, i + 1);
		}
		return strRet;
	}

	private static String intToBig(String intPart) {
		int grade;
		String result = "";
		String strTmp = "";
		grade = intPart.length() / GRADE;
		if (intPart.length() % GRADE != 0)
			grade += 1;
		for (int i = grade; i >= 1; i--) {
			strTmp = getNowGradeVal(intPart, i);
			result += getSubUnit(strTmp);
			result = dropZero(result);
			if (i > 1)
				if (getSubUnit(strTmp).equals("零零零零")) {
					result += "零" + GRADEUNIT.substring(i - 1, i);
				} else {
					result += GRADEUNIT.substring(i - 1, i);
				}
		}
		return result;
	}

	private static String getNowGradeVal(String strVal, int grade) {
		String rst;
		if (strVal.length() <= grade * GRADE)
			rst = strVal.substring(0, strVal.length() - (grade - 1) * GRADE);
		else
			rst = strVal.substring(strVal.length() - grade * GRADE, strVal
					.length()
					- (grade - 1) * GRADE);
		return rst;
	}

	private static String getSubUnit(String strVal) {
		String rst = "";
		for (int i = 0; i < strVal.length(); i++) {
			String s = strVal.substring(i, i + 1);
			int num = Integer.parseInt(s);
			if (num == 0) {
				if (i != strVal.length())
					rst += "零";
			} else {
				rst += NUM.substring(num, num + 1);
				if (i != strVal.length() - 1)
					rst += UNIT.substring(i + 4 - strVal.length(), i + 4
							- strVal.length() + 1);
			}
		}
		return rst;
	}

	private static String dropZero(String strVal) {
		String strRst;
		String strBefore;
		String strNow;
		strBefore = strVal.substring(0, 1);
		strRst = strBefore;
		for (int i = 1; i < strVal.length(); i++) {
			strNow = strVal.substring(i, i + 1);
			if (strNow.equals("零") && strBefore.equals("零"))
				;
			else
				strRst += strNow;
			strBefore = strNow;
		}
		if (strRst.substring(strRst.length() - 1, strRst.length()).equals("零"))
			strRst = strRst.substring(0, strRst.length() - 1);
		return strRst;
	}

	
	
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~_！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

    public static Map<String,String> createMoneyByGroup(String testStr){
		String entireStr = "";
		String none = "零";
		String ten = "拾";
		String million = "万";
		String zeroStr = "";
		Map<String,String> moneyMap = new HashMap<String,String>(0);
		String[] entireMoney = new String[]{"元","拾","佰","仟","万"};
		String[] entireMoneyAdd = new String[]{"万","仟","佰","拾","元"};
		String[] zeroMoney = new String[]{"角","分"};
		if(testStr == null || "".equals(testStr)){
			return moneyMap;
		}
		if(testStr.indexOf("元") == -1){
			zeroStr = testStr;
		}else{
			zeroStr = testStr.substring(testStr.indexOf("元")+1);
			entireStr = testStr.substring(0,testStr.indexOf("元")+1);
		}
		
		if(!"".equals(zeroStr)){
			for(String zero : zeroMoney){
				if(zeroStr.indexOf(zero) != -1){
					moneyMap.put(zero, zeroStr.substring(zeroStr.indexOf(zero)-1,zeroStr.indexOf(zero)));
				}else{
					moneyMap.put(zero, none);
				}
			}
		}
		
		if(!"".equals(entireStr)){
			for(String entire : entireMoney){
				if(entireStr.lastIndexOf(entire) != -1 
						&& entireStr.lastIndexOf(entire) == 1){
					moneyMap.put(entire, entireStr.substring(entireStr.lastIndexOf(entire)-1,entireStr.lastIndexOf(entire)));
					break;
				}else
				if(entireStr.lastIndexOf(entire) != -1 
						&& entireStr.lastIndexOf(entire) > 0 
						&& !none.equals(entireStr.substring(entireStr.lastIndexOf(entire)-2, 
								entireStr.lastIndexOf(entire)-1))){
					moneyMap.put(entire, entireStr.substring(entireStr.lastIndexOf(entire)-1,entireStr.lastIndexOf(entire)));
				}else if(entireStr.lastIndexOf(entire) != -1 
						&& entireStr.lastIndexOf(entire) > 0 
						&& none.equals(entireStr.substring(entireStr.lastIndexOf(entire)-2, 
								entireStr.lastIndexOf(entire)-1))){
					moneyMap.put(entire, entireStr.substring(entireStr.lastIndexOf(entire)-1,entireStr.lastIndexOf(entire)));
				}
			}
			
		}
		boolean isContinue = true;
		for(String entireAdd : entireMoneyAdd){
			if(isContinue == true && entireStr.indexOf(entireAdd) == -1){
				continue;
			}
			isContinue = false;
			if(million.equals(entireAdd) && 
			   ten.equals(entireStr.substring(entireStr.indexOf(entireAdd)-1, entireStr.indexOf(entireAdd)))){
				moneyMap.put(entireAdd, none);
			}else if(!moneyMap.containsKey(entireAdd)){
				moneyMap.put(entireAdd, none);
			}
			
		}
	
		return moneyMap;
    }
    
    public static String switchBigMoney(String money){
    	String m = "";
    	if("0".equals(money))
                 m = "零";
    	else if("1".equals(money))
            	 m = "壹";
    	else if("2".equals(money))
            	 m = "贰";
    	else if("3".equals(money))
           	     m = "叁";
    	else if("4".equals(money))
              	 m = "肆";
    	else if("5".equals(money))
           	     m = "伍";
    	else if("6".equals(money))
           	     m = "陆";
    	else if("7".equals(money))
           	     m = "柒";
    	else if("8".equals(money))
          	     m = "捌";
    	else if("9".equals(money))
          	     m = "玖";
		return m;

    }
    
    public static String createMoneyByTen(String entireStr){
    	String tenStr = "";
    	String ten = "拾";
		if(!"".equals(entireStr) && entireStr.split(ten).length > 2){
			String tenstr = entireStr.substring(0,entireStr.lastIndexOf(ten));
			tenStr = tenstr.substring(tenstr.lastIndexOf(ten)-1,tenstr.lastIndexOf(ten));
		}
		return tenStr;
    }
    
	public static void main(String[] args) {
//		System.out.println("----------------------------------------------------");
//		System.out.println(StringUtil.getBigArea(10052345.00));
//		System.out.println(StringUtil.getBigArea(0.00));
//		System.out.println(StringUtil.getBigArea(0.6));
//		System.out.println(StringUtil.getBigArea(0.609));
//		System.out.println(StringUtil.getBigArea(150.21));
//		System.out.println(StringUtil.getBigArea(111115400089666.23));
//		System.out.println(StringUtil.getBigArea(22200004444.23));
//		System.out.println(StringUtil.getBigArea(10005.0691));
//		System.out.println("----------------------------------------------------");
//		System.out.println(StringUtil.getBigMoney(10005));
//		System.out.println(StringUtil.switchBigMoney("0"));
        BigDecimal bd=new BigDecimal("1111111.1212");
        bd.toString();
		
	}

}
