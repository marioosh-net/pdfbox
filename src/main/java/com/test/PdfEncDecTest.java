package com.test;

import java.io.InputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class PdfEncDecTest {

	public final static String PDF_OWNER_PASSWORD = "cd1j";
	public final static String PDF_USER_PASSWORD = "";	
	
	public static void main(String[] args) throws Exception {
		
		InputStream in = PdfEncDecTest.class.getClassLoader().getResourceAsStream("filed5b3.pdf");
		PDDocument document = PDDocument.load(in);
		AccessPermission ap = new AccessPermission();
		ap.setCanPrint(true);
		ap.setCanExtractContent(false);
		ap.setCanExtractForAccessibility(false);
	    StandardProtectionPolicy spp = new StandardProtectionPolicy(PDF_OWNER_PASSWORD, PDF_USER_PASSWORD, ap);
        document.protect(spp);
        document.save("out.pdf");
        document.close();
		
		PDDocument doc = PDDocument.load("out.pdf");
		if(doc.isEncrypted()) {
			StandardDecryptionMaterial sdm = new StandardDecryptionMaterial(PDF_OWNER_PASSWORD);
			doc.openProtection(sdm); // org.apache.pdfbox.exceptions.CryptographyException: Error: The supplied password does not match either the owner or user password in the document.
			doc.decrypt(PDF_OWNER_PASSWORD); // the same like above
		}
		doc.close();
	}

}
