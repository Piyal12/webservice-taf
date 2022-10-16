package com.im.service.util.report;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import com.im.service.common.Config;

public class HtmlReport {

	public static String HTML_REPORT_FILE_NAME = "htmlreport.html";
	public static String TEST_OUT_FILE_NAME = "testoutput.txt";
	public static String TEST_HTML_REPORT_FILE;
	public static String TEST_OUT_FILE;
	
	public static final String HTML_REPORT_START = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"<meta name=\"description\" content=\"Child Rows\">\n" + 
			"    <title></title>\n" + 
			"	<meta charset=\"utf-8\" />\n" + 
			"    <link href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\" rel=\"stylesheet\" integrity=\"sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN\" crossorigin=\"anonymous\">\n" + 
			"\n" + 
			"    <script src=\"https://code.jquery.com/jquery-3.1.0.js\"></script>\n" + 
			"    <link href=\"https://nightly.datatables.net/css/jquery.dataTables.css\" rel=\"stylesheet\" type=\"text/css\" />\n" + 
			"    <script src=\"https://cdn.datatables.net/1.10.13/js/jquery.dataTables.js\"></script>\n" + 
			"    <script src=\"https://cdn.datatables.net/select/1.2.1/js/dataTables.select.min.js\" type=\"text/javascript\"></script>\n" + 
			"\n" + 
			"<style>\n" + 
			"        td.details-control {\n" + 
			"            text-align:center;\n" + 
			"            color:forestgreen;\n" + 
			"    cursor: pointer;\n" + 
			"}\n" + 
			"tr.shown td.details-control {\n" + 
			"    text-align:center; \n" + 
			"    color:red;\n" + 
			"}\n" + 
			"</style>\n" + 
			"</head>\n" + 
			"<body style=\"font-family: Arial, Helvetica, sans-serif; font-size:10px\">\n" + 
			"    <table width=\"100%\" class=\"display\" id=\"example\" cellspacing=\"0\">\n" + 
			"        <thead>\n" + 
			"            <tr>\n" + 
			"                <th></th>\n" + 
			"                <th>Service Name</th>\n" + 
			"                <th>Test Name</th>\n" + 
			"                <th>Test Status</th>\n" + 
			"                <th>Response Time</th>\n" + 
			"            </tr>\n" + 
			"        </thead>\n" + 
			"        <tfoot>\n" + 
			"            <tr>\n" + 
			"                <th></th>\n" + 
			"                <th>Service Name</th>\n" + 
			"                <th>Test Name</th>\n" + 
			"                <th>Test Status</th>\n" + 
			"                <th>Response Time</th>\n" + 
			"            </tr>\n" + 
			"        </tfoot>\n" + 
			"    </table>\n" + 
			"\n" + 
			"<script>\n" + 
			"\n" + 
			"\n" + 
			"     $(document).ready(function () {\n" + 
			"\n" + 
			"         var table = $('#example').DataTable({\n" + 
			"             \"data\": testdata.data,\n" + 
			"             select:\"single\",\n" + 
			"             \"columns\": [\n" + 
			"                 {\n" + 
			"                     \"className\": 'details-control',\n" + 
			"                     \"orderable\": false,\n" + 
			"                     \"data\": null,\n" + 
			"                     \"defaultContent\": '',\n" + 
			"                     \"render\": function () {\n" + 
			"                         return '<i class=\"fa fa-plus-square\" aria-hidden=\"true\"></i>';\n" + 
			"                     },\n" + 
			"                     width:\"15px\"\n" + 
			"                 },\n" + 
			"                 { \"data\": \"servicename\" },\n" + 
			"                 { \"data\": \"testname\" },\n" + 
			"                 { \"data\": \"teststatus\" },\n" + 
			"                 { \"data\": \"responsetime\" }\n" + 
			"             ],\n" + 
			"             \"order\": [[1, 'asc']]\n" + 
			"         });\n" + 
			"\n" + 
			"         // Add event listener for opening and closing details\n" + 
			"         $('#example tbody').on('click', 'td.details-control', function () {\n" + 
			"             var tr = $(this).closest('tr');\n" + 
			"             var tdi = tr.find(\"i.fa\");\n" + 
			"             var row = table.row(tr);\n" + 
			"\n" + 
			"             if (row.child.isShown()) {\n" + 
			"                 // This row is already open - close it\n" + 
			"                 row.child.hide();\n" + 
			"                 tr.removeClass('shown');\n" + 
			"                 tdi.first().removeClass('fa-minus-square');\n" + 
			"                 tdi.first().addClass('fa-plus-square');\n" + 
			"             }\n" + 
			"             else {\n" + 
			"                 // Open this row\n" + 
			"                 row.child(format(row.data())).show();\n" + 
			"                 tr.addClass('shown');\n" + 
			"                 tdi.first().removeClass('fa-plus-square');\n" + 
			"                 tdi.first().addClass('fa-minus-square');\n" + 
			"             }\n" + 
			"         });\n" + 
			"\n" + 
			"         table.on(\"user-select\", function (e, dt, type, cell, originalEvent) {\n" + 
			"             if ($(cell.node()).hasClass(\"details-control\")) {\n" + 
			"                 e.preventDefault();\n" + 
			"             }\n" + 
			"         });\n" + 
			"     });\n" + 
			"\n" + 
			"    function format(d){\n" + 
			"        \n" + 
			"         // `d` is the original data object for the row\n" + 
			"         return '<table cellpadding=\"5\" cellspacing=\"0\" border=\"0\" style=\"padding-left:50px;\">' +\n" + 
			"             '<tr>' +\n" + 
			"                 '<td>API:</td>' +\n" + 
			"                 '<td>' + d.api + '</td>' +\n" + 
			"             '</tr>' +\n" + 
			"             '<tr>' +\n" + 
			"                 '<td>Pay Load:</td>' +\n" + 
			"                 '<td>' + d.payload + '</td>' +\n" + 
			"             '</tr>' +\n" + 
			"             '<tr>' +\n" + 
			"                 '<td>Response Status Code</td>' +\n" + 
			"                 '<td>' + d.responsestatuscode + '</td>' +\n" + 
			"             '</tr>' +\n" + 
			"			  '<tr>' +\n" + 
			"                 '<td>Response Body:</td>' +\n" + 
			"                 '<td>' + d.responsebody + '</td>' +\n" + 
			"             '</tr>' +\n" + 
			"         '</table>';  \n" + 
			"    }\n" + 
			"\n" + 
			"    var testdata = {\n" + 
			"    \"data\": [";
	
	
	public static final String HTML_REPORT_END = "   ]\n" + 
			"    };\n" + 
			"</script>\n" + 
			"</body>\n" + 
			"</html>";
	
	public static void init () {
			try {	
				File f = new File (Config.TEST_HTML_REPORT_DIR);
				if (f.exists())
					FileUtils.deleteDirectory(f);
				f.mkdir();
				
				HtmlReport.TEST_HTML_REPORT_FILE = Config.TEST_HTML_REPORT_DIR + "htmlreport.html";
				HtmlReport.TEST_OUT_FILE = Config.TEST_HTML_REPORT_DIR + "testoutput.txt";
				
				Files.createFile(Paths.get(HtmlReport.TEST_OUT_FILE));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void createHtmlReport () {
		try {
			String content = new String(Files.readAllBytes(Paths.get(HtmlReport.TEST_OUT_FILE)));
			if (content.length() > 0) {
		    	content = content.substring(0,content.lastIndexOf(","));
				Files.deleteIfExists(Paths.get(HtmlReport.TEST_HTML_REPORT_FILE));
		    	Files.createFile(Paths.get(HtmlReport.TEST_HTML_REPORT_FILE));
				Files.write(Paths.get(HtmlReport.TEST_HTML_REPORT_FILE), (HtmlReport.HTML_REPORT_START).getBytes(), StandardOpenOption.APPEND);
		    	Files.write(Paths.get(HtmlReport.TEST_HTML_REPORT_FILE), ("\r\n"+content).getBytes(), StandardOpenOption.APPEND);
		    	Files.write(Paths.get(HtmlReport.TEST_HTML_REPORT_FILE), ("\r\n"+HtmlReport.HTML_REPORT_END).getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
	}
	
}
