const apiUrlBooks = '/library/api/books/';
const apiUrlPathNewComment = '/comment?newCommentText='

export function loadAllBooks() {
    return fetch(apiUrlBooks)
        .then(res => res.json())
        .catch(console.log)
}

export function createOrUpdateBook(book) {
    return fetch(apiUrlBooks, {
        method: book.id === null ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(book)
    }).then(response => response.json())
}

export function addCommentToBook(bookId, commentText) {
    return fetch(apiUrlBooks + bookId + apiUrlPathNewComment + commentText, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'}
    }).then(response => response.json());
}

export function deleteBook(book) {
    return fetch(apiUrlBooks + book.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    });
}