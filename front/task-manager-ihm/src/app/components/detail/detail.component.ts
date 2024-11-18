import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MessageService } from "primeng/api";
import { Task } from "../../models/task";
import { TaskService } from "../../services/task.service";

@Component({
  selector: "detail",
  templateUrl: "./detail.component.html",
  styleUrls: ["./detail.component.scss"],
})
export class DetailComponent implements OnInit {
  /**
   * Tache à afficher.
   */
  public task: Task = {
    label: "",
    description: "",
    completed: false,
  };

  public isCreation: boolean = false;

  /**
   * Constructeur du composant.
   * @param taskService Service de gestion des tâches.
   * @param messageService Service de gestion des messages.
   * @param router Service de gestion des routes.
   * @param route Service de gestion des routes.
   */
  constructor(
    private taskService: TaskService,
    private messageService: MessageService,
    private router: ActivatedRoute,
    private route: Router
  ) {}

  /**
   * Méthode d'initialisation du composant.
   */
  ngOnInit(): void {
    console.debug("Init DetailComponent");
    const taskId = this.router.snapshot.paramMap.get("id");

    if (taskId && isNaN(Number(taskId))) {
      this.isCreation = true;
    } else {
      this.taskService.get(+taskId!).subscribe((task) => {
        this.task = task;
      });
    }
  }

  /**
   * Méthode de suppression d'une tâche.
   * @param task Tâche à supprimer.
   */
  public deleteTask(): void {
    this.taskService.delete(this.task.id!).subscribe({
      next: () => {
        this.logSuccess("La tâche a été supprimée avec succès");
        this.goHome();
      },
      error: () =>
        this.logError(
          "Une erreur est survenue lors de la suppression de la tâche"
        ),
    });
  }

  /**
   * Méthode de mise à jour de l'état d'une tâche.
   */
  public update(): void {
    this.taskService.update(this.task).subscribe({
      next: (task) => {
        this.logSuccess("La tâche a été mise à jour avec succès");
        this.goHome();
      },
      error: () =>
        this.logError(
          "Une erreur est survenue lors de la mise à jour de la tâche"
        ),
    });
  }

  /**
   * Méthode de création d'une tâche.
   */
  public create(): void {
    this.taskService.create(this.task).subscribe({
      next: (task) => {
        this.logSuccess("La tâche a été créée avec succès");
        this.goHome();
      },
      error: () =>
        this.logError(
          "Une erreur est survenue lors de la création de la tâche"
        ),
    });
  }

  /**
   * Méthode de redirection vers la page d'accueil.
   */
  private goHome(): void {
    this.route.navigateByUrl("/");
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
