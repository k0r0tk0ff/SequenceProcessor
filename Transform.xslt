<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes" />
    <xsl:template match="entry">
        <entry field = "{field}" />
    </xsl:template>

    <xsl:template match="entries">
        <entries>
            <xsl:apply-templates />
        </entries>
    </xsl:template>
</xsl:stylesheet>