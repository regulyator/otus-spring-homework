const apiUrlAuthors = '/library/api/authors/';

export function loadAllAuthors() {
    return fetch(apiUrlAuthors)
        .then(res => res.json())
        .catch(console.log)
}

export function createOrUpdateAuthor(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.author)
    };
    return fetch(apiUrlAuthors, requestOptions)
        .then(response => response.json());
}

export function deleteAuthor(author) {
    return fetch(apiUrlAuthors + author.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).catch(reason =>
        console.log(reason)
    )
}