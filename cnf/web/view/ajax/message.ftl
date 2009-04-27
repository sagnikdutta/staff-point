<#-- @ftlvariable name="message" type="ru.point.view.Message" -->
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
