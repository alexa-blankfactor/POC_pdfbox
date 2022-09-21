package com.itelinc;

import com.itelinc.models.CustomerInformationCwp;
import com.itelinc.utils.ReadPdfContent;
import com.opencsv.bean.CsvToBeanBuilder;
import org.testng.annotations.Test;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PdfTest {

    @Test
    public void testPdf() {
        String filePdf="src/test/resources/pdfFile/CWP.pdf";
        String fileTestData = "src/test/resources/dataTest/customer_information_cwp.csv";

        try {
            List<CustomerInformationCwp> customerInformationCwps= new CsvToBeanBuilder(new FileReader(fileTestData))
                    .withType(CustomerInformationCwp.class)
                    .withSeparator(';')
                    .withSkipLines(0)
                    .build()
                    .parse();
            String pdfContent =  new ReadPdfContent().getContent(filePdf);
            System.out.println(pdfContent);
            assertThat(pdfContent).contains(customerInformationCwps.get(0).getCustomer())
                    .contains(customerInformationCwps.get(0).getControlNumber())
                    .contains(customerInformationCwps.get(0).getCustID())
                    .contains(customerInformationCwps.get(0).getAddress())
                    .contains(customerInformationCwps.get(0).getDateRcvd())
                    .contains(customerInformationCwps.get(0).getDateInv())
                    .contains(customerInformationCwps.get(0).getAddCust())
                    .contains(customerInformationCwps.get(0).getAdjuster())
                    .contains(customerInformationCwps.get(0).getRep())
                    .contains(customerInformationCwps.get(0).getComments());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPdf2() {
        String filePdf="src/test/resources/pdfFile/CWP.pdf";
        String imageExpected = "src/test/resources/images/example.png";
        String actualImage= "src/test/resources/images/image_1.png";
        try {
            new ReadPdfContent().getImage(filePdf);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertThat(new ReadPdfContent().sendImageCompare(imageExpected,actualImage)>0).isEqualTo(true);

    }



}
