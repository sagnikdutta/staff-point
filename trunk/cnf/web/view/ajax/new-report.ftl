<#-- @ftlvariable name="report" type="ru.point.model.Report" -->
<#include "../common/macro.ftl">
<li><@period report=report/>
    ${report.text?html?replace("\n", "<br/>")} </li>