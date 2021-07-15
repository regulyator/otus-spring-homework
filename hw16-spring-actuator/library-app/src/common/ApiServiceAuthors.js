const apiUrlAuthors = '/library/api/authors/';


export function loadAllAuthors() {
    return fetch(apiUrlAuthors)
        .then(response => response.json())
        .catch(error =>
            alert(error)
        );
}

export function createOrUpdateAuthor(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.author)
    };
    return fetch(create ? apiUrlAuthors : this.state.author._links.self.href, requestOptions);
}

export function deleteAuthor(author) {
    return fetch(author._links.self.href, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    });
}