package com.prosnav.oms.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class IdGen {
	/*
	 * 	调用
	 * String LEDGER_CODE = "1";
	 * 	Long code = IdGen.newId();
		LEDGER_CODE = code.toHexString(IdGen.newId());
		或者
		String code = IdGen.newStringId();
	 */
	

	private static int _nextInc = (new java.util.Random()).nextInt(0xffffff);
	private static int getTime() {
		return _flip( (int)(System.currentTimeMillis()/1000) );
	}
	 
	    static int _flip( int x ){
	            byte b[] = new byte[4];
	            ByteBuffer bb = ByteBuffer.wrap( b );
	            bb.order( ByteOrder.LITTLE_ENDIAN );
	            bb.putInt( x );
	            bb.flip();
	            bb.order( ByteOrder.BIG_ENDIAN );
	            int v = bb.getInt();
	            return v;
	    }

	    
	    
	    
	    private IdGen() {
	    	
			
		}


		private static long toLong(int h, int low) {
			long high = ((long)h)<<32;
//	    	System.out.println(Integer.toBinaryString(h));
//	    	System.out.println(Integer.toBinaryString(low));
			long value = high|(low&0xffffffffl);
//			System.out.println(Long.toBinaryString(value));
			return value;
		}
		
		public static String newStringId(){
			return Long.toHexString(newId());
		}

		/**
		 * 生成唯一ID
		 * ID组成 共8个字节
		 * 前4个字节是时间戳，第5个字节是机器号（手动配置的机器号），最后3个字节是自增数
		 * @return
		 */
		public static long newId() {
			
			int inc = 0;
			synchronized (IdGen.class) {
				 inc = _nextInc++;
			}
			int mac_inc =inc|getMachineNumber()<<24;
			return toLong(getTime(),mac_inc);

		}
		/**
		 * 
		 * @param firmId
		 * @return
		 */
		public static String random(String firmId){
	        
	        Random rand = new Random();
	       int result= rand.nextInt(100000000);
	        Date date = new Date();
			 String simple = new SimpleDateFormat("yyyyMMdd").format(date);
			 String res =simple+firmId+result;
	        
	       
	        return res;
	    }
		
	   
		
	
	public static byte getMachineNumber() {
		return 0;
	}
	/** 
	* 字符串+1方法，该方法将其结尾的整数+1,适用于任何以整数结尾的字符串,不限格式，不限分隔符。 
	* @author xsh
	* @param testStr 要+1的字符串 
	* @return +1后的字符串 
	* @exception NumberFormatException 
	*/ 
	public static String addOne(String testStr){ 
	    //String[] strs = testStr.split("[^0-9]");//根据不是数字的字符拆分字符串 
	    String numStr =  testStr.substring(testStr.length()-2,testStr.length());//取出最后2位数字 
	    if(numStr != null && numStr.length()>0){//如果最后一组没有数字(也就是不以数字结尾)，抛NumberFormatException异常 
	        //int n = numStr.length();//取出字符串的长度 
	        int num = Integer.parseInt(numStr)+1;//将该数字加一 
	        String added = String.valueOf(num); 
	        //n = Math.min(n, added.length()); 
	        //拼接字符串 
	        return testStr.subSequence(0, testStr.length()-added.length())+added; 
	    }else{ 
	        throw new NumberFormatException(); 
	    } 
	}
	
	static byte[] toByteArray(int x)
	{
		return new byte[]{int3(x),int2(x),int1(x),int0(x)};
	}
	
	//获取int左边第一个字节
    private static byte int3(int x) { return (byte)(x >> 24); }
    private static byte int2(int x) { return (byte)(x >> 16); }
    private static byte int1(int x) { return (byte)(x >>  8); }
    private static byte int0(int x) { return (byte)(x >>  0); }
    
    public static String fmtMicrometer(String text)  
    {  
        DecimalFormat df = null;  
        if(text.indexOf(".") > 0)  
        {  
            if(text.length() - text.indexOf(".")-1 == 0)  
            {  
                df = new DecimalFormat("###,##0.");  
            }else if(text.length() - text.indexOf(".")-1 == 1)  
            {  
                df = new DecimalFormat("###,##0.0");  
            }else  
            {  
                df = new DecimalFormat("###,##0.00");  
            }  
        }else   
        {  
            df = new DecimalFormat("###,##0");  
        }  
        double number = 0.0;  
        try {  
             number = Double.parseDouble(text);  
        } catch (Exception e) {  
            number = 0.0;  
        }  
        return df.format(number);  
    }  

}
