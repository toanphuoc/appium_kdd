<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<head>
				<meta charset="UTF-8"/>
				<title></title>
				<style type="text/css">
					body{
						padding: 50px;
					}
					table, th, td {
					    border: 1px solid black;
					    border-collapse: collapse;
					}
			
					table thead{
						background-color: #3366FF;
					}
			
					table thead td{
						padding: 10px;
						text-align: center;
						font-weight: bold;
					}
			
					.top-report{
						width: 100%;
						text-align: center;
					}
			
					.top-report tr td{
						padding: 10px;
					}
					
					.top{
						width:60%; 
						margin:0 auto; 
						margin-bottom: 40px; 
						
					}
				
					.inforDevice{
						border: 1px solid;
					    padding: 10px;
					    font-weight: 600;
					}
					
					.detail{
						border-right: 1px solid;
						border-left: 1px solid;
						border-bottom: 1px solid;
    					padding: 10px;
					}
				</style>
			</head>
			<body>
				<div class="top">
					<table class="top-report">
						<tr>
							<td colspan="6" style="font-weight: bold;"><xsl:value-of select="TestSuite/Header/SuiteName" /></td>
						</tr>
						<tr>
							<td><strong>Reporter</strong></td>
							<td><strong>Total Passed</strong></td>
							<td><strong>Total Failed</strong></td>
							<td><strong>Result</strong></td>
							<td><strong>Duration</strong></td>
							<td><strong>Date</strong></td>
						</tr>
						<tr>
							<td><xsl:value-of select="TestSuite/Header/Reporter" /></td>
							<td><xsl:value-of select="TestSuite/Header/PASSED" /></td>
							<td><xsl:value-of select="TestSuite/Header/FAILED" /></td>
							<xsl:if test="TestSuite/Header/FAILED = '0'">
								<td><strong style="color: green;">PASSED</strong></td>
							</xsl:if>
							<xsl:if test="TestSuite/Header/FAILED != '0'">
								<td><strong style="color: red;">FAILED</strong></td>
							</xsl:if>
							<td><xsl:value-of select="TestSuite/Header/Duration" /></td>
							<td><xsl:value-of select="TestSuite/Header/Date" /></td>
						</tr>
					</table>
					<table class="top-report">
						<tr>
							<td colspan="4" style="font-weight: bold;">Information Device</td>
						</tr>
						<tr>
							<td><strong>Device Name</strong></td>
							<td><strong>Platform</strong></td>
							<td><strong>OS Version</strong></td>
							<td><strong>UDID</strong></td>
						</tr>
						<tr>
							<td><xsl:value-of select="TestSuite/Header/Device/Name"></xsl:value-of></td>
							<td><xsl:value-of select="TestSuite/Header/Device/OS"></xsl:value-of></td>
							<td><xsl:value-of select="TestSuite/Header/Device/Version"></xsl:value-of></td>
							<td><xsl:value-of select="TestSuite/Header/Device/UDID"></xsl:value-of></td>
						</tr>
					</table>
				</div>
				<table style="width:60%; margin:0 auto;">
						<thead>
							<td width="5%">ID</td>
							<td width="80%">Test case</td>
							<td width="15%">Result</td>
						</thead>
						<tbody>
							<xsl:for-each select="TestSuite/Body/TestCase">
								<tr>
									<td style="text-align: center"><xsl:value-of select="position()"></xsl:value-of></td>
									<td style="padding: 10px;  cursor: pointer;" onclick="location.href='{url}';">
										<xsl:value-of select="TestName" />
									</td>
									<td style="padding: 10px; text-align: center;">
										<xsl:if test="Result = 'true'" >
											<strong style="color: green;">PASSED</strong>
										</xsl:if>
										<xsl:if test="Result = 'false'" >
											<strong style="color: red;">FAILED</strong>
										</xsl:if>
									</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>