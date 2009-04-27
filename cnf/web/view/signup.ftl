<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="message" type="ru.point.view.Message" -->
<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head>�����������</@head>
<body>
<div class="header">
        <span>
            <a href="/">�������</a>
        </span>
    <@search/>
    <@loginForm session=session/>
</div>
<div class="contol">
    <@inout session=session/>
</div>
<div class="box">

    <div id="boxheader">
        <h1 class="top">�����������<span class="tip red"></span></h1>
        <span class="position">������ ���������!</span>
    </div>

    <#if message?? && message.text?has_content>
    <div class="message <#if message.success>success</#if>">
        ${message.text}
        <ul>
            <#list message.list as line>
            <li>${line}</li>
            </#list>
        </ul>
    </div>
    </#if>

    <h1>� &mdash; Point, � ��?<span class="tip blue"></span></h1>

    <div id="contactFormMessage"> </div>

    <form id="newUserForm" action="/user" method="post">
        <table class="contacts">
            <tr>
                <th>���:</th>
                <td><input name="name" type="text"/></td>
            </tr>
            <tr>
                <th>�������:</th>
                <td><input name="surname" type="text"/></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <th>E-Mail (�� ���������������� �����):</th>
                <td><input name="email" type="text" value="${email!""}"/></td>
            </tr>
            <tr>
                <th>&nbsp;</th>
            </tr>
            <tr>
                <th>������:</th>
                <td><input name="password" type="password"/></td>
            </tr>
            <tr>
                <th>��� ��� ������:</th>
                <td><input name="again" type="password"/></td>
            </tr>
        </table>

        <a class="action submit" href="#">������������������</a>
    </form>

</div>
<@foot/>
</body>
</html>
