<#import "parts/common.ftlh" as c>

<@c.page>
    <h4>List of transactions</h4>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Category</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Message</th>
            <th>Transaction Date</th>
        </tr>
        </thead>
        <tbody>
        <#list transactions as transaction>
            <tr>
                <td>${transaction.id}</td>
                <td>${transaction.category.category}</td>
                <td>${transaction.isIncome?string("Income", "Expanse")}</td>
                <td>${transaction.amount} MDL</td>
                <td>${transaction.message}</td>
                <td>${transaction.transactionDate}</td>
                <td><a href="/transactions/${transaction.id}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>