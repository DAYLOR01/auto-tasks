const topics = document.querySelector('#topics');
const loadMore = document.querySelector('#loadMore')
var pageNumber = 1;
const pageSize = 10;

const loadTopics = () => {
    loadMore.setAttribute('disabled', 'disabled')
    fetch(`http://localhost:8081/api/topics?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
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
            for (let topic of output.elements) {
                innerContent += `
                <div class="bg-light p-4 mb-4 rounded">
                    <h2>${topic.header}</h2>
                    <p class="lead">${topic.content}</p>
                    <p>Автор: ${topic.author.name}</p>
                </div>
                `
            }
            topics.innerHTML += innerContent;
            if(pageNumber < output.totalPages) {
                loadMore.removeAttribute('disabled')
                pageNumber++;
            }
        })
        .catch(function(reason){
            console.log('error: ' + reason)
        })
}

document.addEventListener('DOMContentLoaded', function (){
    loadTopics();
})