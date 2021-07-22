const apiUrlAuthors = '/library/api/authors/';


export function loadAllAuthors() {
    return fetch(apiUrlAuthors)
        .then(response => response.json())
        .catch(error =>
            alert(error)
        );
}

export function createOrUpdateAuthor(author) {
    return fetch(author.id === null ? apiUrlAuthors : author._links.self.href, {
        method: author.id === null ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(author)
    });
}

export function deleteAuthor(author) {
    return fetch(author._links.self.href, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    });
}