import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

public class BarcodeGeneratorUtil 
{
	public BarcodeGeneratorUtil()
	{
		
	}
	public static void main(String args[]) throws IOException, WriterException
	{
		String path=args[0];
		String barCodeNum=args[1];
		String typeOfBarcode=args[2];
		System.out.println("Before creating");
		new BarcodeGeneratorUtil().generateBarcode(barCodeNum,path,typeOfBarcode);
	}
	public void generateBarcode(String barcodeNum,String path,String typeOfBarcode) throws IOException, WriterException
	{
		String basePath=path;
		String barCodeImageName=createFileName(basePath,barcodeNum,typeOfBarcode);
		BitMatrix bitMatrix=BarcodeGeneratorUtil.createBarCode(barcodeNum,typeOfBarcode);
		BarcodeGeneratorUtil.writeGeneratedBarToFileSystem(bitMatrix,barcodeNum,barCodeImageName,typeOfBarcode);
	}

	public static String uniqueNumberGeneration(String id,String typeOfBarcode,String truckNum)
	{
		if(typeOfBarcode.equalsIgnoreCase("Truck"))
		{
			String uniqueNum="TK"+id+"00000"+truckNum;
			return uniqueNum;
		}
		else if(typeOfBarcode.equalsIgnoreCase("Bin"))
		{
			String uniqueNum="BN00000"+id;
			return uniqueNum;
		}
		return "";
	}

	public static String createFileName(String basePath,String barCodeNum,String type)
	{
		String fileName="BC_"+barCodeNum+"_"+type;
		return basePath+"//"+fileName+".png";
	}

	public static BitMatrix createBarCode(String barCodeNum,String type) throws IOException, WriterException
	{
		BitMatrix bitMatrix = new Code128Writer().encode(barCodeNum, BarcodeFormat.CODE_128, 150, 80);
		return bitMatrix;
	}

	public static void writeGeneratedBarToFileSystem(BitMatrix bitMatrix,String barCodeNum,String fileName,String type) throws IOException
	{
		OutputStream out=null;
		try
		{
			out=new FileOutputStream(new File(fileName));
			MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
		}
		finally
		{
			if(null!=out)
			{
				out.close();
			}
		}
	}
}