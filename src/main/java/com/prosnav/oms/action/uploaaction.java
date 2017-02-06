package com.prosnav.oms.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.prosnav.common.Constants;
import com.prosnav.core.jwt.domain.User;
import com.prosnav.oms.dao.upDao;
import com.prosnav.oms.util.ReadExcelTODB;

public class uploaaction extends ActionSupport{
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	  private String usename ;  
	    private File file1 ; //具体上传文件的 引用 , 指向临时目录中的临时文件  
	    private String file1FileName ;  // 上传文件的名字 ,FileName 固定的写法  
	    private String file1ContentType ; //上传文件的类型， ContentType 固定的写法  
	    private long cust_id5;
	    private String file;
	    private String fileFileName;
		public String getFile() {
			return file;
		}
		public void setFile(String file) {
			this.file = file;
		}
		
		public String getFileFileName() {
			return fileFileName;
		}
		public void setFileFileName(String fileFileName) {
			this.fileFileName = fileFileName;
		}
		// private long cust_id5;
	    public void setCust_id5(long cust_id5) {
			this.cust_id5 = cust_id5;
		}
		public String getUsename() {  
	        return usename;  
	    }  
	    public void setUsename(String usename) {  
	        this.usename = usename;  
	    }  
	    public File getFile1() {  
	        return file1;  
	    }  
	    public void setFile1(File file1) {  
	        this.file1 = file1;  
	    }  
	    public String getFile1FileName() {  
	        return file1FileName;  
	    }  
	    public void setFile1FileName(String file1FileName) {  
	        this.file1FileName = file1FileName;  
	    }  
	    public String getFile1ContentType() {  
	        return file1ContentType;  
	    }  
	    public void setFile1ContentType(String file1ContentType) {  
	        this.file1ContentType = file1ContentType;  
	    }  
	public void load(){
		try{
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
	        //获取文件存储路径  
	        @SuppressWarnings("deprecation")
			/*String path = ServletActionContext.getRequest().getRealPath("/upload"); */
	        
	        String path = "/var/www/oms";
	        //输出流  
	        //String file1FileName = "ajax.txt";
	        request.getParameter("file1");
	        OutputStream os = new FileOutputStream(new File(path,file1FileName));  
	        //输入流  
	        InputStream is = new FileInputStream(file1);  
	          
	        byte[] buf = new byte[1024];  
	        int length = 0 ;  
	          
	        while(-1 != (length = is.read(buf) ) )  
	        {  
	            os.write(buf, 0, length) ;  
	        }  
	        is.close();  
	        os.close(); 
	        
	        PrintWriter out;
	        /*response.setContentType("text;charset=utf-8");
	        response.setCharacterEncoding("UTF-8");*/
	        out = response.getWriter();
	       
			out.print("<script>alert('上传成功');location.href='cust_detail.action?cust_id="+cust_id5+"&flag=1'</script>");
			out.close();
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
		
		/*return "success";*/
	}
//添加kyc
	public String kycinsert(){
		try{
	        //获取文件存储路径  
	       // @SuppressWarnings("deprecation")
			String path = "C:/Users/qinzw/Desktop/amc/KYC.xlt";  
	        //输出流  
	        //String file1FileName = "ajax.txt";
			//long cust_id = Long.parseLong((String) request.getParameter("cust_id5"));
	        String data = ReadExcelTODB.data(file1);
			String[] datas = data.split(",");
	        upDao.kycinsert(datas,cust_id5);
	       request.setAttribute("cust_id", cust_id5);
	       return "success";
	        }catch(Exception e){
	        	e.printStackTrace();
	        	return "error";
	        }
		
	}
	
	public void send_cust_files() {
		User user = (User) request.getSession().getAttribute(Constants.USER);
		if(user == null) {
		}
		String dirs = Constants.SEND_CUST_EMAIL+user.get_id();
		File file = new File(dirs);
		if(!file.exists() && !file.isDirectory()) {
			try {
				Files.createDirectories(Paths.get(dirs));
			} catch (IOException e) {
				e.printStackTrace();
			}
			boolean f = file.mkdirs();
			System.out.println(f);
		}
		try {
			OutputStream out = new FileOutputStream(new File(file,fileFileName));
		     //输入流  
	        InputStream in = new FileInputStream(this.file);  
	        byte[] buffer = new byte[1024];  
	        int byteread = 0; 
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
            out.close();
            in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	   
	}
}
