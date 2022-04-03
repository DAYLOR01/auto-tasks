const taskList = document.getElementById('taskList')
const loadMoreBtn = document.getElementById('loadMore')
const taskStatusMap = {
    OPEN: "Открыта",
    IN_PROGRESS: "В процессе",
    UNDER_REVIEW: "На проверке",
    COMPLETED: "Завершена",
    CLOSED: "Отменена"
}
let pageNumber = 1;
const pageSize = 10;

document.addEventListener('DOMContentLoaded', function () {
    loadMoreBtn.addEventListener('click', loadTasks)
    reloadTasks()
})

const loadTasks = () => {
    loadMoreBtn.setAttribute('disabled', 'disabled')
    fetch(`${apiUrl}/tasks/page?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
        headers: header
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            return value.json();
        })
        .then(function(output){
            for (const task of output.elements) {
                let innerContent = ""
                let itemStyle;
                let inspDate = task.inspirationDate ? new Date(task.inspirationDate).setHours(0, 0, 0, 0) : null,
                    today = new Date().setHours(0, 0, 0, 0);
                console.log(`${inspDate}, ${today}, ${inspDate < today}`)
                if (task.status === 'COMPLETED' || task.status === 'CLOSED')
                    itemStyle = "list-group-item-secondary"
                else if (inspDate && inspDate === today)
                    itemStyle = "list-group-item-warning"
                else if (inspDate && inspDate < today)
                    itemStyle = "list-group-item-danger"
                else itemStyle = ""
                innerContent += `
                <li class="list-group-item ${itemStyle}">
                    <p class="h5"><a href="/tasks?id=${task.id}">#${task.id}</a> Создана ${task.authorUser.name}. Назначена на ${task.assignedUser.name}. ${taskStatusMap[task.status]}</p>
                    <p class="fs-5">${task.header}</p>
                    <div class="row row-cols-lg-5 row-cols-2">
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
                </li>
                `
                taskList.innerHTML += innerContent;
            }
            if(pageNumber < output.totalPages) {
                loadMoreBtn.removeAttribute('disabled')
                pageNumber++;
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const reloadTasks = () => {
    pageNumber = 1;
    taskList.innerHTML = '';
    loadTasks();
}