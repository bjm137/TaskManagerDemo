import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { Task } from "../../models/task";
import { TaskService } from "../../services/task.service";

@Component({
  selector: "home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
})
export class HomeComponent implements OnInit {
  /**
   * Liste des tâches.
   */
  public tasks: Task[] = [];

  /**
   * Constructeur du composant.
   * @param taskService Service de gestion des tâches.
   */
  constructor(
    private taskService: TaskService,
    private messageService: MessageService,
    private router: Router
  ) {}

  /**
   * Méthode d'initialisation du composant.
   */
  ngOnInit(): void {
    this.taskService.getAll().subscribe((tasks) => {
      this.tasks = tasks;
    });
  }

  /**
   * Méthode de suppression d'une tâche.
   * @param task Tâche à supprimer.
   */
  public deleteTask(task: Task): void {
    this.taskService.delete(task.id!).subscribe({
      next: () => {
        this.tasks = this.tasks.filter((t) => t.id !== task.id);
        this.logSuccess("La tâche a été supprimée avec succès");
      },
      error: () => {
        this.logError(
          "Une erreur est survenue lors de la suppression de la tâche"
        );
      },
    });
  }

  /**
   * redirection vers la page de création de tâche et d'édition.
   * @param taskId Tâche à mettre à jour.
   */
  public editTask(taskId: number): void {
    this.router.navigateByUrl(`/tasks/${taskId}`);
  }

  /**
   * Méthode de mise à jour de l'état d'une tâche.
   * @param task Tâche à mettre à jour.
   */
  public updateState(task: Task): void {
    task.completed = !task.completed;
    this.taskService.update(task).subscribe({
      next: () => {
        this.logSuccess("La tâche a été mise à jour avec succès");
      },
      error: () => {
        this.logError(
          "Une erreur est survenue lors de la mise à jour de la tâche"
        );
      },
    });
  }

  /**
   * Méthode de gestion des erreurs.
   * @param errorMsg Message d'erreur.
   */
  private logError(errorMsg: string): void {
    this.messageService.add({
      severity: "error",
      summary: "Erreur",
      detail: errorMsg,
    });
  }

  /**
   * Méthode de gestion des succès.
   * @param successMsg Message de succès.
   */
  private logSuccess(successMsg: string): void {
    this.messageService.add({
      severity: "success",
      summary: "Succès",
      detail: successMsg,
    });
  }
}
