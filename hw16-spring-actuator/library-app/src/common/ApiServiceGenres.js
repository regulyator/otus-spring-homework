const apiUrlGenres = '/library/api/genres/';

export function loadAllGenres() {
    return fetch(apiUrlGenres)
        .then(res => res.json())
        .catch(console.log)
}

export function createOrUpdateGenre(create) {
    const requestOptions = {
        method: create ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.genre)
    };

    return fetch(create ? apiUrlGenres : this.state.genre._links.self.href, requestOptions);
}

export function deleteGenre(genre) {
    return fetch(genre._links.self.href, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    });
}