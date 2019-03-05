package core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class S3bucketurl {
	
	
	private final static boolean chunks = true;
	static String fileToEncode;
	static String encodedFilePath = "C:\\Testcases\\Api\\encoded.txt";
	
	static String apiURL = "http://10.0.8.131/api/UploadDocs/UploadFile/fileObject";
	
	public static String GetUrl(String FileName, String FileData,String FileSize, String FileExtension,String FileType, String Source,String FilePath) {

		JSONObject jsonObject = null;
		String finalURL = "";
		try {
			
			
			fileToEncode = FilePath;
			encode(fileToEncode, encodedFilePath, chunks);
			String Base64String = "";

			InputStream inputStream = new FileInputStream(encodedFilePath);
			InputStreamReader reader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(reader);
			String line;
			while ((line = br.readLine()) != null) {
				Base64String += line;
			}

			System.out.println("String Created :" + Base64String);
			
			FileData = Base64String;

			Date dt = new Date();

			SimpleDateFormat smdt = new SimpleDateFormat("dd.MM.yyyy");

			String datee = smdt.format(dt);

			System.out.println("Date : " + datee);

			String jSonDataString = "{'FileName': '" + FileName + "', 'FileData':'" + FileData
					+ "','FileSize': '" + FileSize + "','FileExtension': '" + FileExtension + "', 'FileType':'" + FileType
					+ "','Source': '" + Source + "'}";

			jsonObject = new JSONObject(jSonDataString);

			System.out.println(jsonObject);

			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL(apiURL);
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
				String decodedString = "";
				
				while ((decodedString = in.readLine()) != null) {
		
					//decodedString = decodedString + in.readLine().toString();
					System.out.println(decodedString);
					
					JSONObject response1 = new JSONObject(decodedString);
					
					System.out.println("Result URL : " + response1.get("S3Url"));
					
					finalURL = (String) response1.get("S3Url");
			
				    
				}
				
				System.out.println("Final URL : " +finalURL);
				
				System.out.println("\n REST Service Invoked Successfully..");
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalURL;
	}

	
	
	private static void encode(String sourceFile, String targetFile, boolean isChunked) throws Exception {

		byte[] base64EncodedData = Base64.encodeBase64(loadFileAsBytesArray(sourceFile), isChunked);

		writeByteArraysToFile(targetFile, base64EncodedData);
	}

	public void decode(String sourceFile, String targetFile) throws Exception {

		byte[] decodedBytes = Base64.decodeBase64(loadFileAsBytesArray(sourceFile));

		writeByteArraysToFile(targetFile, decodedBytes);
	}

	public static byte[] loadFileAsBytesArray(String fileName) throws Exception {

		File file = new File(fileName);
		int length = (int) file.length();
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[length];
		reader.read(bytes, 0, length);
		reader.close();
		return bytes;

	}

	public static void writeByteArraysToFile(String fileName, byte[] content) throws IOException {

		File file = new File(fileName);
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file));
		writer.write(content);
		writer.flush();
		writer.close();

	}
	
	
	public String GetFileUrl(String FileName, String pfilePath, String appNo, String type)
			throws IOException, URISyntaxException {
		String content = "";
		JSONObject docObj = new JSONObject();
		try {
			CloseableHttpClient client = HttpClientBuilder.create().build();
			StringBuilder payLoad = new StringBuilder("{").append("\"ApplicationNo\":\"").append(appNo).append("\"}");
			String encoSt = URLEncoder.encode(payLoad.toString(), "UTF-8");
			String url = "http://api.policybazaar.com/cs/repo/uploadInsurerPortalDoc?payloadJSON=" + encoSt;
			HttpPost post = new HttpPost(url);

			File file = new File(pfilePath);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			if (type.equalsIgnoreCase("Image")) {
				builder.addBinaryBody("file", file, ContentType.create("image/jpg"), pfilePath);
			} else {
				builder.addBinaryBody("file", file, ContentType.create("application/pdf"), pfilePath);
			}
			HttpEntity entity = builder.build();
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			content = EntityUtils.toString(response.getEntity());
			docObj = new JSONObject(content);
			System.out.println("output URL:" + content.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return docObj.getString("docUrl");
	}


}
