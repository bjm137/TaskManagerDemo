import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { Observable, of } from "rxjs";
import { catchError } from "rxjs/operators";

import { Task } from "../models/task";

@Injectable({ providedIn: "root" })
export class TaskService {
  private uri = "http://localhost:8080/tasks";

  httpOptions = {
    headers: new HttpHeaders({ "Content-Type": "application/json" }),
  };

  constructor(private http: HttpClient) {}

  /**
   * Récupère la liste des tâches.
   * @returns Liste des tâches.
   */
  public getAll(): Observable<Task[]> {
    return this.http
      .get<Task[]>(this.uri)
      .pipe(catchError(this.handleError<Task[]>(this.getAll.name, [])));
  }

  /**
   * Crée une tâche.
   * @param task  Tâche à créer.
   * @returns La tâche créée avec son identifiant.
   */
  public create(task: Task): Observable<Task> {
    return this.http
      .post<Task>(`${this.uri}/create`, task, this.httpOptions)
      .pipe(catchError(this.handleError<Task>(this.create.name)));
  }

  /**
   * Récupère une tâche par son identifiant.
   * @param id Identifiant de la tâche.
   * @returns La tâche correspondant à l'identifiant.
   */
  public get(id: number): Observable<Task> {
    const url = `${this.uri}/${id}`;
    return this.http
      .get<Task>(url)
      .pipe(catchError(this.handleError<Task>(`${this.get.name} id=${id}`)));
  }

  /**
   * Recherche des tâches par critères.
   * @param term Terme de recherche.
   * @returns Liste des tâches correspondant au terme de recherche.
   */
  public search(term: string): Observable<Task[]> {
    if (!term.trim()) {
      return of([]);
    }

    return this.http
      .get<Task[]>(`${this.uri}/search=${term}`)
      .pipe(catchError(this.handleError<Task[]>(this.search.name, [])));
  }

  /**
   * Supprime une tâche par son identifiant.
   * @param id Identifiant de la tâche à supprimer.
   * @returns La tâche supprimée.
   */
  public delete(id: number): Observable<Task> {
    const url = `${this.uri}/${id}`;

    return this.http
      .delete<Task>(url, this.httpOptions)
      .pipe(catchError(this.handleError<Task>(this.delete.name)));
  }

  /**
   * Met à jour une tâche.
   * @param task Tâche à mettre à jour.
   * @returns La tâche mise à jour.
   */
  public update(task: Task): Observable<any> {
    return this.http
      .put(this.uri, task, this.httpOptions)
      .pipe(catchError(this.handleError<any>(this.update.name)));
  }

  /**
   * Handle les erreurs http.
   *
   * @param operation - Nom de l'opération qui a échoué.
   * @param result - Valeur optionnelle à retourner comme résultat observable.
   */
  private handleError<T>(operation = "operation", result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return !!result ? of(result as T) : error;
    };
  }
}
