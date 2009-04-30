<#-- @ftlvariable name="report" type="ru.point.model.Report" -->
<#include "../common/macro.ftl">
<li><@period report=report/>
    ${report.text?html?replace("\n", "<br/>")}
    <a href="#" class="hidden pseudo delete right" rel="/report/delete/${report.id}" onclick="return false;">удалить</a></li>