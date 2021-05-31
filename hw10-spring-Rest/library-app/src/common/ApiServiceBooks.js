const apiUrlBooks = '/library/api/books/';
const apiUrlPathNewComment = '/comment?newCommentText='

export function loadAllBooks() {
    return fetch(apiUrlBooks)
        .then(res => res.json())
        .then((data) => {
            return data;
        })
        .catch(console.log)
}

export function loadBook(bookId) {
    return fetch(apiUrlBooks + bookId)
        .then(res => res.json())
        .then((data) => {
            return data;
        })
        .catch(console.log)
}

export function createOrUpdateBook(book) {
    return fetch(apiUrlBooks, {
        method: book.id === null ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(book)
    })
        .then(response => response.json())
        .then(data => {
            return data
        });
}

export function addCommentToBook(bookId, commentText) {
    return fetch(apiUrlBooks + bookId + apiUrlPathNewComment + commentText, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'}
    })
        .then(response => response.json())
        .then(data => {
            return data
        });
}

export function deleteBook(book) {
    return fetch(apiUrlBooks + book.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
            if (response.ok) {
                alert("Book deleted!");
            } else {
                response.text().then(text => {
                    alert(text)
                })
            }
        }
    ).catch(reason => console.log(reason))
}