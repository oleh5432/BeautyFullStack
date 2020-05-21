<#import "parts/basePartPage.ftl" as b>
<#import "parts/stylesheet.ftl" as sl>
<#import "parts/script.ftl" as sc>

    <#macro stylesheet>
        <@sl.stylesheet "static/css/main_only.css"></@sl.stylesheet>
    </#macro>

    <#macro script>
        <@sc.script "static/mainScript.js"></@sc.script>
    </#macro>

<@b.basePartPage "Beauty salon" stylesheet script>
    <div class="container">
    </div>
</@b.basePartPage>