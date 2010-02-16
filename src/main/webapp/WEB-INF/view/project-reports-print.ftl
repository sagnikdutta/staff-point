<#-- @ftlvariable name="reports" type="java.util.List<ru.point.model.Report>" -->
<#-- @ftlvariable name="project" type="ru.point.model.Project" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>! - Point</title>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
    <link type="text/css" rel="stylesheet" href="/css/print.css"/>
</head>
<body>
<h1>${project.name} &mdash; Consolidated Weekly Report </h1>
<h2>2009-08-01 &mdash; 2009-08-08</h2>

<#list reports as report>

<h3>${report.reportForActivity.name}</h3>

<ul>
    <li>1</li>
    <li>2</li>
    <li>3</li>
</ul>

</#list>

</body>
</html>
