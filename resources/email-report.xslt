<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<style type="text/css" media="screen">
			.id{
				text-align: center;
			}
			.name{
				padding-left: 5px;
			}
	
			.pass{
				text-align: center;
				color:  green;
				font-weight: bold;
			}
			
			.fail{
				text-align: center;
				color:  red;
				font-weight: bold;
			}
		</style>
		<h4>Setup:</h4>
		<ul>
			<li><strong>MDM: </strong> <xsl:value-of select="TestSuite/Header/App/MDM" /></li>
			<li><strong>Application: </strong> <xsl:value-of select="TestSuite/Header/App/Name" /></li>
			<li><strong>Device: </strong><xsl:value-of select="TestSuite/Header/Device/Name" /> (<xsl:value-of select="TestSuite/Header/Device/Version" />)</li>
		</ul>
		<h4>Result:</h4>
		<ul>
			<li><strong>Process:</strong> 100%</li>
			<li><strong>Current pass rate: </strong><xsl:value-of select="TestSuite/Header/CurrentPassed" />%</li>
			<li><strong>No run:</strong> 0%</li>
		</ul>
		<table border="1" width="100%"> 
			<thead>
				<tr>
					<th>ID</th>
					<th>Name Test Case</th>
					<th>Result</th>
				</tr>
			</thead>
			<tbody>
				<xsl:for-each select="TestSuite/Body/TestCase">
					<tr>
						<td class="id"><xsl:value-of select="position()"></xsl:value-of></td>
						<td class="name">
							<xsl:value-of select="TestName" />
						</td>
						<xsl:if test="Result = 'true'" >
							<td class="pass">PASSED</td>
						</xsl:if>
						<xsl:if test="Result = 'false'" >
							<td class="fail">FAILED</td>
						</xsl:if>
					</tr>
				</xsl:for-each>
			</tbody>
		</table>
	</xsl:template>
</xsl:stylesheet>