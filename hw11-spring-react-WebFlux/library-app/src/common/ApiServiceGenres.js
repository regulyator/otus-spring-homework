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

    return fetch(apiUrlGenres, requestOptions)
        .then(response => response.json());
}

export function deleteGenre(genre) {
    return fetch(apiUrlGenres + genre.id, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    }).catch(reason => console.log(reason))
}