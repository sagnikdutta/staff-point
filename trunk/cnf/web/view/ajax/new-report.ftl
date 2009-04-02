<#-- @ftlvariable name="report" type="ru.point.model.Report" -->
<li>${report.start.time?date} ~ ${report.end.time?date}:<br/>
    ${report.text?html?replace("\n", "<br/>")} </li>