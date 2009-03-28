<#-- @ftlvariable name="session" type="ru.point.model.Session" -->
<#-- @ftlvariable name="user" type="ru.point.model.User" -->
<#include "common/macro.ftl">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head></@head>
<body>
<div class="header">
        <span>
            <a href="/">Главная</a> &rarr;
            <#--<@projectRef project=user.mainPosition.project /> &rarr;-->
            <#--<@userRef user=user />-->
        </span>
    <@search/>
    <#--<@inout session=session/>-->
</div>
<div class="box">
    <div class="datepick">
        <div class="row days">
            <div class="column">Week</div>
            <div class="column">Monday</div>
            <div class="column">Tuesday</div>
            <div class="column">Wednesday</div>
            <div class="column">Thursday</div>
            <div class="column">Friday</div>
        </div>
        <div class="row">
            <div class="column">W29/2009</div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
        <div class="row">
            <div class="column">W28/2009</div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
        <div class="row">
            <div class="column">W27/2009</div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
        <div class="row">
            <div class="column">W26/2009</div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
        <div class="row">
            <div class="column">W25/2009</div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
            <div class="column"></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<@foot/>
</body>
</html>
