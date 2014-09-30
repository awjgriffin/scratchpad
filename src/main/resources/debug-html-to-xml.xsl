<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
<xsl:output method="xml" />

	<xsl:template match="/">	
		<jarFiles>
			<xsl:for-each select="/root/td">
				<jarFile><xsl:value-of select="a[substring(text(), string-length(text())-2, string-length(text()))='jar']" /></jarFile>
			</xsl:for-each>			
		</jarFiles>
	</xsl:template>
	
</xsl:stylesheet>