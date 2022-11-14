<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>Transaction editor</h4>
    <br>
    <form action="/transactions" method="post">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <div class="form-group row">
            <label for="transactionId" class="col-sm-2 col-form-label">Transaction ID</label>
            <div class="col-sm-10">
                <input type="text" readonly class="form-control-plaintext" value="${transactionEdit.id}" name="id"
                       id="transactionId">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <label for="transactionId" class="col-sm-2 col-form-label">Category</label>
            <div class="col-sm-10">
            <select class="form-select" name="category" id="transactionId">
                <#list categories as category>
                    <option>${category.category}</option>
                </#list>
            </select>
            </div>
        </div>
        <br>
        <div class="form-group row">
            <label for="message" class="col-sm-2 col-form-label">Message</label>
            <div class="col-sm-10">
                <input type="text" id="message" name="message" class="form-control" value="${transactionEdit.message}"
                       placeholder="Message">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <label for="tAmount" class="col-sm-2 col-form-label">Amount</label>
            <div class="col-sm-10">
                <input type="number" id="tAmount" name="amount" class="form-control"
                       placeholder="${transactionEdit.amount}"
                       step="0.01" min="0" value="${transactionEdit.amount}">
            </div>
        </div>
        <br>
        <div class="form-group row">
            <label for="tDate" class="col-sm-2 col-form-label">Date</label>
            <div class="col-sm-10">
                <input type="date" id="tDate" name="transactionDate" class="form-control" placeholder="Date"
                       value="${transactionEdit.transactionDate}">
            </div>
        </div>

        <div class="form-group row">
            <div class="col-sm-10">
                <button class="btn btn-outline-primary mt-3" type="submit">Save</button>

                <a href="/transactions/delete/${transactionEdit.id}" class="btn btn-outline-danger mt-3" >Delete</a>
            </div>
        </div>

    </form>
</@c.page>