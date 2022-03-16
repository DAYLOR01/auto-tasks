const openTasks = document.getElementById('openTasks')
const inProgressTasks = document.getElementById('inProgressTasks')
const underReviewTasks = document.getElementById('underReviewTasks')
const completedTasks = document.getElementById('completedTasks')
const closedTasks = document.getElementById('closedTasks')

document.addEventListener('DOMContentLoaded', function () {
    let stacks = [
        {status: 'OPEN', lastMonth: false, stackElement: openTasks},
        {status: 'IN_PROGRESS', lastMonth: false, stackElement: inProgressTasks},
        {status: 'UNDER_REVIEW', lastMonth: false, stackElement: underReviewTasks},
        {status: 'COMPLETED', lastMonth: true, stackElement: completedTasks},
        {status: 'CLOSED', lastMonth: true, stackElement: closedTasks}
    ]
    for (const stack of stacks) {
        loadMyTasksToStack(stack.status, stack.lastMonth, stack.stackElement)
    }
})

const loadMyTasksToStack = (status, lastMonth, stackElement) => {
    fetch(`${apiUrl}/tasks/my?status=${status}&lastMonth=${lastMonth}`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            let innerContent = ""
            for (const task of output) {
                innerContent += `
                <div class="card border-primary mb-1">
                    <div class="card-header">
                        <a href="/tasks?id=${task.id}" class="card-link">#${task.id}</a>
                    </div>
                    <div class="card-body pb-0">
                        <p class="card-text">${task.header}</p>
                        <div class="row row-cols-lg-3 row-cols-1">
                `
                for (const tag of task.tags) {
                    innerContent += `
                            <div class="col px-1">
                                <span class="badge bg-primary text-truncate w-100">
                                ${tag.descriptionRU}
                                </span>
                            </div>
                    `
                }
                innerContent += `
                        </div>
                    </div>
                </div>
                `
            }
            stackElement.innerHTML = innerContent;
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}