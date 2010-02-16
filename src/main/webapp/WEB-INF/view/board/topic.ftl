<#-- @ftlvariable name="posts" type="java.util.List<ru.point.model.board.Post>" -->
<#-- @ftlvariable name="topic" type="ru.point.model.board.Topic" -->
<#include "../common/macro.ftl"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Алексей Андреев - QMS</title>
    <@head/>
</head>
<body>
<div class="header">
    <a href="/">Главная</a>
    <@search/>
</div>
<div class="box one">
    <h1 class="top">${topic.title}</h1>
    <table id="board">
        <#list posts as post>
        <tr id="${post.id}">
            <th><@userRef user=post.user/><br/>
                <span>${post.time.time?datetime?string.long}</span></th>
            <td>${post.text}</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
            <td><a href="#reply" class="reply">ответить</a>&nbsp;&darr;</td>
        </tr>
        <tr>
            <th>&nbsp;</th>
        </tr>
        </#list>
        <#if user??>
            <tr id="reply">
                <th><@userRef user=user/></th>
                <td><textarea rows="7" cols="80">Мой ответ чемберлену</textarea><input type="submit" value="post"></td>
            </tr>
        </#if>
    </table>
</div>
<@foot/>
</body>
</html>