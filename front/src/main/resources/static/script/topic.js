const topicsDiv = document.getElementById('topics');
const loadMoreBtn = document.getElementById('loadMore');
const newTopicBtn = document.getElementById('newTopic');
let pageNumber = 1;
const pageSize = 10;

document.addEventListener('DOMContentLoaded', function (){
    loadMoreBtn.addEventListener('click', loadTopics);
    newTopicBtn.addEventListener('click', createNewTopic);
    reloadTopics();
})

const loadTopics = () => {
    loadMoreBtn.setAttribute('disabled', 'disabled')
    fetch(`${apiUrl}/topics?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
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
            for (const topic of output.elements) {
                innerContent += `
                <div class="bg-light p-4 mb-4 rounded">
                    <h2>${topic.header}</h2>
                    <p class="lead">${topic.content.replace('\n','<br/>')}</p>
                    <p>Автор: ${topic.author.name}</p>
                </div>
                `
            }
            topicsDiv.innerHTML += innerContent;
            if(pageNumber < output.totalPages) {
                loadMoreBtn.removeAttribute('disabled')
                pageNumber++;
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

const reloadTopics = () => {
    pageNumber = 1;
    topicsDiv.innerHTML = '';
    loadTopics();
    newTopicBtn.removeAttribute('disabled');
}

const createNewTopic = () => {
    topicsDiv.innerHTML = `
        <div class="bg-light p-4 mb-4 rounded border border-2 border-secondary">
            <form>
                <div class="mb-3">
                    <input type="text" class="form-control form-control-lg" id="topicHeader" maxlength="150" placeholder="Тақырыбы">
                </div>
                <div class="mb-3">
                    <textarea type="text" class="form-control" id="topicContent" rows="4" placeholder="Мәтін"></textarea>
                </div>
                <div class="mb-3">
                    <button type="button" class="btn btn-danger" id="cancelNewTopic">Қайтару</button>
                    <button type="button" class="btn btn-success" id="saveTopic">Сақтау</button>
                </div>
            </form>
        </div>
        ${topicsDiv.innerHTML}
    `
    document.getElementById('cancelNewTopic').addEventListener('click', reloadTopics)
    document.getElementById('saveTopic').addEventListener('click', saveNewTopic)
    newTopicBtn.setAttribute('disabled', 'disabled');
}

const saveNewTopic = () => {
    let topicHeader = document.getElementById('topicHeader').value
    let topicContent = document.getElementById('topicContent').value

    fetch(`${apiUrl}/topics`, {
        method: 'POST',
        headers: header,
        body: JSON.stringify({
            header: topicHeader,
            content: topicContent
        })
    })
        .then(function(value){
            if (value.status !== 200){
                return Promise.reject(new Error(value.status));
            }
            reloadTopics();
            newTopicBtn.removeAttribute('disabled');
            return value.json();
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}