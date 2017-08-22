<%@page import="com.sharedcloud.pc.stati.GStatic"%>
<%@page import="com.sharedcloud.pc.front.pageModel.FPageUsers"%>
<%@page import="com.sharedcloud.pc.utils.*,java.io.*,sun.misc.*,com.sharedcloud.pc.model.*,com.sharedcloud.pc.front.model.*"%>
<%
String pic=request.getParameter("pic");
String pic1=request.getParameter("pic1");
String pic2=request.getParameter("pic2");
String pic3=request.getParameter("pic3");

String path = Users.HEADIMGPATH;
String userid = ((FPageUsers)request.getSession().getAttribute(Users.SESSION_USER_NAME)).getUserid();

if(pic!=null&&!pic.equals("")){
	//åå¾
	File file = new File(path+userid+".png");
	FileOutputStream fout = null;
	fout = new FileOutputStream(file);
	fout.write(new BASE64Decoder().decodeBuffer(pic));
	fout.close();
}

//å¾1
File file1 = new File(path+userid+"_162.png");
FileOutputStream fout1 = null;
fout1 = new FileOutputStream(file1);
fout1.write(new BASE64Decoder().decodeBuffer(pic1));
fout1.close();

//å¾2
File file2 = new File(path+userid+"_48.png");
FileOutputStream fout2 = null;
fout2 = new FileOutputStream(file2);
fout2.write(new BASE64Decoder().decodeBuffer(pic2));
fout2.close();

//å¾3
File file3 = new File(path+userid+"_20.png");
FileOutputStream fout3 = null;
fout3 = new FileOutputStream(file3);
fout3.write(new BASE64Decoder().decodeBuffer(pic3));
fout3.close();



out.println("{\"status\":1}");
%>