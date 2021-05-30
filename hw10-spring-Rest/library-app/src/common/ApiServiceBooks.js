const apiUrlBooks = '/library/api/books/';

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

export function createOrUpdateBook(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.book)
    };

    fetch(apiUrlBooks, requestOptions)
        .then(response => response.json())
        .then(data => this.setState({book: data}))
        .then(() => alert(create ? 'Book created!' : 'Book updated!'));
}

export function deleteBook(book) {
    return fetch(apiUrlBooks + book.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
            if (response.ok) {
                alert("Genre deleted!");
            } else {
                response.text().then(text => {
                    alert(text)
                })
            }
        }
    ).catch(reason => console.log(reason))
}