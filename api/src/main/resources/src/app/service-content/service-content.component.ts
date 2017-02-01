import {Component, OnInit, AfterViewInit, ElementRef} from '@angular/core';
import { HighlightJsService } from 'angular2-highlight-js';

@Component({
  selector: 'sm-service-content',
  templateUrl: './service-content.component.html',
  styleUrls: ['./service-content.component.css']
})
export class ServiceContentComponent implements OnInit, AfterViewInit {

  sampleContent: String = JSON.stringify({sample: 'something', number: 123})

  constructor(private el: ElementRef,private highlightService: HighlightJsService) { }

  ngOnInit() {
  }

  ngAfterViewInit(){
    this.highlightService.highlight(this.el.nativeElement.querySelector('.json'));
  }

}
