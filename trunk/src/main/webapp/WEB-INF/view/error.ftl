<#-- @ftlvariable name="exception" type="java.lang.Exception" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Случилась хрень</@head>
<body>

<div id="highlight">
</div>

<div class="header">
        <span>
            <a href="/">Главная</a>
        </span>
    <@search />
    <@login />
</div>
<div id="info" class="box one">
    <h2>случилась хрень: ${exception.getMessage()}</h2>
    <a href="#" class="toggle" rel="trace">возможно из-за этого:</a></p>
    <div id="trace" class="hidden">
        <pre>
     <#list exception.getStackTrace() as el>
         ${el.getClassName()}.${el.getMethodName()} (${el.getFileName()}:${el.getLineNumber()})
     </#list>
    </pre>
    </div>
</div>
<@foot />
</body>
</html>
