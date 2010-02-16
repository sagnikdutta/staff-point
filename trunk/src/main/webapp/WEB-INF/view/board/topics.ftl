<#-- @ftlvariable name="topics" type="java.util.List<ru.point.model.board.Topic>" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#include "../common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>Главная</@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a>
        </span>
    <@search />
    <@inout session=session/>
</div>
<div id="info" class="box">
    <h1 class="top">Форум</h1>
    <table id="board">
        <#if session??>
        <tr class="new">
            <td><@userRef user=session.user/></td>
            <td>text</td>
        </tr>
        </#if>
        <#list topics as topic>
        <tr id="${topic.id}">
            <th><@userRef user=topic.topicStartTime.time?datetime?string /><br/>
                <#--<span>${post.time.time?datetime?string.long}</span></th>-->
                <td>${topic.title}</td>
        </tr>
        </#list>
    </table>
</div>
<@foot />
</body>
</html>
