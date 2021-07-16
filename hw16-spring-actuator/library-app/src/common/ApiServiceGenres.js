const apiUrlGenres = '/library/api/genres/';

export function loadAllGenres() {
    return fetch(apiUrlGenres)
        .then(res => res.json())
        .catch(console.log)
}

export function createOrUpdateGenre(genre) {
    return fetch(genre.id === null ? apiUrlGenres : genre._links.self.href, {
        method: genre.id === null ? 'POST' : 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(genre)
    });
}

export function deleteGenre(genre) {
    return fetch(genre._links.self.href, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json'}
    });
}