import { Component, Input } from "@angular/core";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"],
})
export class HeaderComponent {
  /**
   * Titre de la page.
   */
  @Input()
  public title!: string;

  constructor() {}

  public goCreateTask(): void {
    console.log("goCreateTask");
    window.location.href = "/tasks/create";
  }
}
