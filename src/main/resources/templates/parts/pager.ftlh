<#function removeSizeAndPageParams filters>
    <#assign result = []>
    <#list filters?split('&') as param>
        <#if !param?starts_with("size=") && !param?starts_with("page=")>
            <#assign result += [param]>
        </#if>
    </#list>
    <#return result?join("&")>
</#function>

<#macro pager url page filters="">
    <#assign cleanedFilters = removeSizeAndPageParams(filters)>

    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        head = (pageNumber >= 4)?then([1, -1], [1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        bodyBefore = (pageNumber >= 4 && pageNumber < totalPages - 1)?then([pageNumber - 1], [])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1], [])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>
    <div class="my-4">
        <ul class="pagination flex-wrap justify-content-center">
            <li class="page-item">
                <div class="dropdown mt-0">
                    <button class="page-link dropdown-toggle" type="button" id="dropdownMenuButton"
                            data-bs-toggle="dropdown" aria-expanded="false">
                        ${page.getSize()}
                    </button>
                    <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <#list [5, 10, 25, 50] as c>
                            <#if c == page.getSize()>
                                <li>
                                    <a class="dropdown-item active" href="#" tabindex="-1">${c}</a>
                                </li>
                            <#else>
                                <li>
                                    <a class="dropdown-item"
                                       href="${url}?${cleanedFilters}&page=<#if page.getNumber() gt page.getTotalElements() / c>${(page.getTotalElements() / c)?round - 1}<#else>${page.getNumber()}</#if>&size=${c}"
                                       tabindex="-1">${c}</a>
                                </li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </li>
            <li class="page-item <#if page.getNumber() == 0>disabled</#if>">
                <a class="page-link" href="${url}?${cleanedFilters}&page=${page.getNumber() - 1}&size=${page.getSize()}"
                   tabindex="-1">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-arrow-left-short" viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5"/>
                    </svg>
                </a>
            </li>
            <#list body as p>
                <#if (p - 1) == page.getNumber()>
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${p}</a>
                    </li>
                <#elseif p == 1 && body?seq_contains(-1) && !(page.getNumber() + 1 == 2)>
                    <li class="page-item">
                        <a class="page-link" href="${url}?${cleanedFilters}&page=${p - 1}&size=${page.getSize()}"
                           tabindex="-1">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-chevron-bar-left" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M11.854 3.646a.5.5 0 0 1 0 .708L8.207 8l3.647 3.646a.5.5 0 0 1-.708.708l-4-4a.5.5 0 0 1 0-.708l4-4a.5.5 0 0 1 .708 0M4.5 1a.5.5 0 0 0-.5.5v13a.5.5 0 0 0 1 0v-13a.5.5 0 0 0-.5-.5"/>
                            </svg>
                        </a>
                    </li>
                <#elseif p == page.getTotalPages() && body?seq_contains(-1) && !(page.getNumber() + 1 == page.getTotalPages() - 1)>
                    <li class="page-item">
                        <a class="page-link" href="${url}?${cleanedFilters}&page=${p - 1}&size=${page.getSize()}"
                           tabindex="-1">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-chevron-bar-right" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M4.146 3.646a.5.5 0 0 0 0 .708L7.793 8l-3.647 3.646a.5.5 0 0 0 .708.708l4-4a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708 0M11.5 1a.5.5 0 0 1 .5.5v13a.5.5 0 0 1-1 0v-13a.5.5 0 0 1 .5-.5"/>
                            </svg>
                        </a>
                    </li>
                <#elseif p == -1>
                <#else>
                    <li class="page-item">
                        <a class="page-link" href="${url}?${cleanedFilters}&page=${p - 1}&size=${page.getSize()}"
                           tabindex="-1">${p}</a>
                    </li>
                </#if>
            </#list>
            <li class="page-item <#if page.getNumber() == page.getTotalPages() - 1>disabled</#if>">
                <a class="page-link" href="${url}?${cleanedFilters}&page=${page.getNumber() + 1}&size=${page.getSize()}"
                   tabindex="-1">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-arrow-right-short" viewBox="0 0 16 16">
                        <path fill-rule="evenodd"
                              d="M4 8a.5.5 0 0 1 .5-.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5A.5.5 0 0 1 4 8"/>
                    </svg>
                </a>
            </li>
        </ul>
    </div>
</#macro>
