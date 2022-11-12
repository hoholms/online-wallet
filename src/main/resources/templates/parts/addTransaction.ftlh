<#macro addTransaction categories isIncome today>
    <form action="/dashboard" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <label>
            <select class="form-control" name="category">
                <#list categories as category>
                    <option>${category.category}</option>
                </#list>
            </select>
        </label>
        <label>
            <input class="form-control" type="number" step="0.01" min="0" name="amount" placeholder="amount">
        </label>
        <label>
            <input class="form-control" type="text" name="message" placeholder="Type message if u want">
        </label>
        <label>
            <input class="form-control" type="date" value="${today}" name="transactionDate">
        </label>
        <#if isIncome>
            <input type="hidden" name="isIncome" value="true">
            <#else>
                <input type="hidden" name="isIncome" value="false">
        </#if>
        <button type="submit" class="btn btn-success">Submit</button>
    </form>
</#macro>