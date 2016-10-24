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

					table tbody td{
					padding: 10px;
					}

					.duration{
					text-align: center;
					}

					.top-report{
					width: 80%;
					margin: 0 auto;
					text-align: center;
					border: 1px solid black;
					margin-bottom: 30px;
					}

					.left{

					width: 80%;
					float: left;
					border-right: 1px solid black;
					font-weight: bold;
					}

					.left div, .right div{
					padding: 10px 0 10px 0;
					border-bottom: 1px solid black;
					}

					.left label, .right label{
					padding: 10px 0 10px 0;
					display: inherit;
					}

					.right{
					font-weight: bold;
					}

					.clr{
					clear: both;
					}

					.imgFailed{
					height: 20%;
					margin-top:20px;
					}
					
					.modal {
					    display: none; /* Hidden by default */
					    position: fixed; /* Stay in place */
					    z-index: 1; /* Sit on top */
					    padding-top: 100px; /* Location of the box */
					    left: 0;
					    top: 0;
					    width: 100%; /* Full width */
					    height: 100%; /* Full height */
					    overflow: auto; /* Enable scroll if needed */
					    background-color: rgb(0,0,0); /* Fallback color */
					    background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
					}
					
					/* Modal Content (image) */
					.modal-content {
					    margin: auto;
					    display: block;
					    width: 80%;
					    max-width: 700px;
					}
					
					/* Caption of Modal Image */
					#caption {
					    margin: auto;
					    display: block;
					    width: 80%;
					    max-width: 700px;
					    text-align: center;
					    color: #ccc;
					    padding: 10px 0;
					    height: 150px;
					}
					
					.close {
					    position: absolute;
					    top: 15px;
					    right: 35px;
					    color: #f1f1f1;
					    font-size: 40px;
					    font-weight: bold;
					    transition: 0.3s;
					}
					
					.close:hover,
					.close:focus {
					    color: #bbb;
					    text-decoration: none;
					    cursor: pointer;
					}
				</style>
			</head>
			<body>
				<div class="top-report">
					<div class="left">
						<div>Test Case</div>
						<xsl:if test="TestCase/Result = 'true'">
							<label style="background: green;">
								<xsl:value-of select="TestCase/TestName" />
							</label>
						</xsl:if>
						<xsl:if test="TestCase/Result = 'false'">
							<label style="background: red;">
								<xsl:value-of select="TestCase/TestName" />
							</label>
						</xsl:if>
					</div>
					<div class="right">
						<div>Durations (ms)</div>
						<xsl:if test="TestCase/Result = 'true'">
							<label style="background: green;">
								<xsl:value-of select="TestCase/Duration" />
							</label>
						</xsl:if>
						<xsl:if test="TestCase/Result = 'false'">
							<label style="background: red;">
								<xsl:value-of select="TestCase/Duration" />
							</label>
						</xsl:if>
					</div>
					<div class="clr"></div>
				</div>
				<table style="width:80%; margin: 0 auto;">
					<thead>
						<td width="5%">Line</td>
						<td width="10%">Keyword</td>
						<td width="55%">Agruments</td>
						<td width="5%">Result</td>
						<td width="10%">Durations</td>
					</thead>
					<tbody>
						<xsl:for-each select="TestCase/TestSteps/Step">
							<xsl:if test="contains(@DoAction, 'DESC')">
								<td style="text-align: center;">
									<xsl:value-of select="@Line" />
								</td>
								<td>
									<strong>
										<xsl:value-of select="@DoAction" />
									</strong>
								</td>
								<td colspan="3">
									<xsl:value-of select="@Agrument" />
								</td>
							</xsl:if>
							<xsl:if test="not(contains(@DoAction, 'DESC'))">

								<xsl:if test="Result = 'true'">
									<tr>
										<td style="text-align: center;">
											<xsl:value-of select="@Line" />
										</td>
										<td>
											<strong>
												<xsl:value-of select="@DoAction" />
											</strong>
										</td>
										<td>
											<xsl:value-of select="@Agrument" />
										</td>
										<td style="text-align: center;">
											<strong style="color: green;">PASSED</strong>
										</td>
										<td class="duration">
											<xsl:value-of select="Duration"></xsl:value-of>
										</td>
									</tr>
								</xsl:if>
								<xsl:if test="Result = 'false'">
									<tr>
										<td style="text-align: center;">
											<xsl:value-of select="@Line" />
										</td>
										<td>
											<strong>
												<xsl:value-of select="@DoAction" />
											</strong>
										</td>
										<td>
											<xsl:value-of select="@Agrument" />
											<xsl:if test="ErrorMessage != ''">
												<br/>
												<lable style="color: red;"><xsl:value-of select="ErrorMessage"></xsl:value-of></lable>
											</xsl:if>
											<xsl:if test="ImageUrl != ''">
												<br/>
												<img class="imgFailed"  src="{ImageUrl}" onClick="zoomOut(this);" style="cursor: pointer;"/>
											</xsl:if>
										</td>
										<td style="text-align: center;">
											<strong style="color: red">FAILED</strong>
										</td>
										<td class="duration">
											<xsl:value-of select="Duration"></xsl:value-of>
										</td>
									</tr>
								</xsl:if>

							</xsl:if>
						</xsl:for-each>
					</tbody>
				</table>
			</body>
			<div id="myModal" class="modal">
				  <span class="close">×</span>
				  <img class="modal-content" id="img01" />
			</div>
			
			<script>
				var modal = document.getElementById('myModal');
				var modalImg = document.getElementById("img01");
				function zoomOut(img){
					console.log(img.src);
					modal.style.display = "block";
				    modalImg.src = img.src;
				}
				
				var span = document.getElementsByClassName("close")[0];

				
				span.onclick = function() {
				    modal.style.display = "none";
				}
			</script>
		</html>
	</xsl:template>
</xsl:stylesheet>