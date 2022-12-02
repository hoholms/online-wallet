<#macro addTransaction categories isIncome today>
    <form class="row g-3" action="/dashboard" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}">

        <div class="col-md-2 form-group">
            <label class="form-label" for="tSelect">Category</label>
            <select class="form-select" name="category" id="tSelect" required>
                <#list categories as category>
                    <option>${category.category}</option>
                </#list>
            </select>
        </div>

        <div class="col-md-2 form-group">
            <label class="form-label" for="tAmount">Amount</label>
            <input class="form-control" type="number" step="0.01" min="0.01" max="1000000" name="amount"
                   placeholder="Amount"
                   id="tAmount" required>
        </div>

        <div class="col-md-2 form-group">
            <label class="form-label" for="tDate">Date</label>
            <input class="form-control" type="date" value="${today}" min="1970-01-01" name="transactionDate" id="tDate"
                   style="height: 2.4rem" required>
        </div>

        <div class="col-md-6 form-group">
            <label class="form-label" for="tMessage">Message</label>
            <input class="form-control" type="text" name="message" placeholder="Type message if you want" id="tMessage">
        </div>

        <#if isIncome>
            <input type="hidden" name="isIncome" value="true">
        <#else>
            <input type="hidden" name="isIncome" value="false">
        </#if>

        <div class="col-12">
            <button type="submit" class="btn btn-success">Submit</button>
        </div>
    </form>
</#macro>