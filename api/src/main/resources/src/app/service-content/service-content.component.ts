import {Component, OnInit, AfterViewInit, ElementRef, ViewChild} from '@angular/core';
import { HighlightJsService } from 'angular2-highlight-js';
import { AceEditorComponent } from 'ng2-ace-editor';

@Component({
  selector: 'sm-service-content',
  templateUrl: './service-content.component.html',
  styleUrls: ['./service-content.component.css']
})
export class ServiceContentComponent implements OnInit, AfterViewInit {
  @ViewChild('editor') editor;

  public text:string = '';
  sampleContent: String = JSON.stringify({sample: 'something', number: 123})

  constructor(private el: ElementRef,private highlightService: HighlightJsService) { }

  ngOnInit() {
  }

  ngAfterViewInit(){
    this.editor.setTheme("eclipse");

    this.editor.getEditor().setOptions({
      enableBasicAutocompletion: true
    });

  }

}
